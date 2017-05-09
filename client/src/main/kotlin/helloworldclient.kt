import io.grpc.ManagedChannelBuilder
import io.grpc.examples.helloworld.GreeterGrpc
import io.grpc.examples.helloworld.HelloRequest
import io.grpc.netty.NegotiationType
import io.grpc.netty.NettyChannelBuilder
import java.util.concurrent.TimeUnit

fun main(args: Array<String>) {
    val channel = ManagedChannelBuilder.forAddress("localhost", 8080).usePlaintext(true).build()
    val blockingStub = GreeterGrpc.newBlockingStub(channel)
//    val request = HelloRequest.newBuilder().setName("Steve").build()
    val builder = HelloRequest.newBuilder()
    with(builder) {
        name = "Steve"
    }
    val request = builder.build()
    try {
        println("Calling server")
        val response = blockingStub.sayHello(request)
        println("Server called")
        println("Response from server: ${response.message}")
    } finally {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS)
    }
}
