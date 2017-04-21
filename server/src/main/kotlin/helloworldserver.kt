import io.grpc.examples.helloworld.GreeterGrpc
import io.grpc.examples.helloworld.HelloReply
import io.grpc.examples.helloworld.HelloRequest
import io.grpc.ServerBuilder
import io.grpc.stub.StreamObserver

class GreeterImpl : GreeterGrpc.GreeterImplBase() {
    override fun sayHello(request: HelloRequest?, responseObserver: StreamObserver<HelloReply>?) {
        var reply = HelloReply.newBuilder().setMessage("Hello ${request?.name}").build()
        responseObserver?.onNext(reply)
        responseObserver?.onCompleted()
    }
}

fun main(args: Array<String>) {
    var server = ServerBuilder.forPort(8080).addService(GreeterImpl()).build()
    server.start()
    println("Server started")
    Runtime.getRuntime().addShutdownHook(Thread() { println("Ups, JVM shutdown") })
    server.awaitTermination()
    println("Server stopped")
}
