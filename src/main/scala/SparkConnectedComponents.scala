import org.apache.spark._
import org.apache.spark.graphx._
import org.apache.spark.graphx.lib._
import org.apache.spark.graphx.util.GraphGenerators


/** Connected components algorithm. */
object SparkConnectedComponents {
	def main(args: Array[String]) {
		if (args.length < 1) {
			System.err.println("Usage: SparkConnectedComponents <file>")
			System.exit(1)
		}
		val fname = args(0)

		val conf = new SparkConf()
		val sc = new SparkContext(conf.setAppName("SparkConnectedComponents(" + fname + ")"))
		
		// Load the graph
		val graph = GraphLoader.edgeListFile(sc, fname)

		def vprog(vertexId: VertexId, value: Int, message: Int): Int = {
			println("Vertex " + vertexId + " gets message " + message)
			if (message == Int.MaxValue) {
				val toRet = vertexId.toInt
				println("Vertex " + vertexId + " returns " + toRet)
				toRet
			} else {
				val toRet = value min message
				println("Vertex " + vertexId + " returns " + toRet)
				toRet
			}
		}

		def sendMsg(triplet: EdgeTriplet[Int, Int]): Iterator[(VertexId, Int)] = {
			if (triplet.dstAttr <= triplet.srcAttr) {
				println("Vertex " + triplet.srcId + " does not send anything to " + triplet.dstId)
				Iterator.empty
			} else {
				println("Vertex " + triplet.srcId + " sends to " + triplet.dstId + " value = " + triplet.srcAttr)
				Iterator((triplet.dstId, triplet.srcAttr))
			}
		}

		def mergeMsg(msg1: Int, msg2: Int): Int = msg1 min msg2

		val minGraph =
			graph.pregel(initialMsg = Int.MaxValue, activeDirection = EdgeDirection.Out)(
				vprog, sendMsg, mergeMsg)

		minGraph.vertices.collect().foreach{
			case (vertexId, value) => println(vertexId + " = " + value)
		}
	}
}
