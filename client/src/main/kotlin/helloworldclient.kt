import io.grpc.examples.helloworld.GreeterGrpc
import io.grpc.examples.helloworld.HelloRequest
import io.grpc.netty.NegotiationType
import io.grpc.netty.NettyChannelBuilder
import java.util.concurrent.TimeUnit

fun main(args: Array<String>) {
    var channel = NettyChannelBuilder.forAddress("localhost", 8080).negotiationType(NegotiationType.PLAINTEXT).build()
    var blockingStub = GreeterGrpc.newBlockingStub(channel)
    var request = HelloRequest.newBuilder().setName("Steve").build()
    try {
        println("Calling server")
        var response = blockingStub.sayHello(request)
        println("Server called")
        println("Response from server: ${response.message}")
    } finally {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }
}
