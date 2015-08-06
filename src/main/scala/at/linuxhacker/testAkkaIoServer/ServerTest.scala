package at.linuxhacker.testAkkaIoServer

import akka.io.{ IO, Tcp }
import akka.actor.{ Actor, ActorSystem, Props }
import akka.actor.ActorLogging
import java.net.InetSocketAddress

class SimplisticHandler extends Actor with ActorLogging {
  
  log.info( "SimplisticHandler Actor started")
  
  import Tcp._
  
  def receive = {
    case Received( data ) =>
      val x = data.decodeString( "utf-8" )
      println( "Received: " + x )
      
    case PeerClosed =>
      log.info( "Peer closed")
      context.stop( self )
  } 
}

class Server extends Actor with ActorLogging {
  
  import Tcp._
  import context.system
  
  IO(Tcp) ! Bind( self, new InetSocketAddress( "0.0.0.0", 4444 ) )
  
  def receive = {
    case b @ Bound( localAddress ) =>
      log.info( "Bound" )
      
    case CommandFailed( _: Bind ) =>
      log.info( "Command failed." )
      context.stop( self )
      
    case c @ Connected( remote, local ) =>
      log.info( "Connection received from hostname: " + remote.getHostName + " address: " + remote.getAddress.toString )
      val handler = context.actorOf( Props[SimplisticHandler] )
      val connection = sender( )
      connection ! Register( handler )
  }
}

object ServerTest extends App {
  
  val system = ActorSystem( "ServerTest" )
  val server = system.actorOf( Props[Server] )
  
  
}