package com.form.docker.demo;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.Collections;
import java.util.stream.Stream;

import static java.lang.System.getenv;

public class Main {

    public static void main( String[] args ) throws Exception {
        String mode = getenv( "mode" );
        int port = Integer.parseInt( getenv( "port" ) );
        if ( "server".equals( mode ) ) {
            new Server( port ).start();
        } else if ( "client".equals( mode ) ) {
            new Client( getenv( "addr" ), port ).start();
        } else {
            throw new IllegalArgumentException();
        }
    }

    public static class Server {

        private final int port;

        public Server( int port ) {
            this.port = port;
        }

        public void start() throws IOException {
            System.out.println( "[SERVER] started" );
            printFiles();

            File file = new File( "/tmp/client_request." + System.currentTimeMillis() );
            file.createNewFile();

            ServerSocket server = new ServerSocket( port );
            while ( true ) {
                try (
                        Socket socket = server.accept();
                        DataInputStream in = new DataInputStream( new BufferedInputStream( socket.getInputStream() ) )
                ) {
                    System.out.println( "[SERVER] accepted connection from client" );
                    String line = in.readUTF();
                    Files.write( file.toPath(), Collections.singletonList( line ), StandardOpenOption.APPEND );
                    System.out.format( "[SERVER] '%s' was written to \n %s\n\n", line, file );
                }
            }
        }

        private void printFiles() {
            System.out.println( "Files in /tmp dir:" );
            Stream.of( new File( "/tmp" ).list() )
                  .filter( it -> it.startsWith( "client_request" ) )
                  .forEach( file -> System.out.println( "\t" + file ) );
            System.out.println( "==================================" );
        }
    }

    public static class Client {

        private final int port;
        private final String address;

        public Client( String address, int port ) {
            this.port = port;
            this.address = address;
        }

        public void start() throws Exception {
            System.out.println( "[CLIENT] started" );
            Thread.sleep( 2000 );
            try (
                    Socket socket = new Socket( address, port );
                    DataOutputStream out = new DataOutputStream( socket.getOutputStream() )
            ) {
                System.out.println( "[CLIENT] connected to server" );
                String str = "Hello " + System.currentTimeMillis();
                out.writeUTF( str );
                System.out.format( "[CLIENT] Message '%s' sent.\n", str );
            }
            System.out.println( "[CLIENT] finished" );
        }
    }
}
