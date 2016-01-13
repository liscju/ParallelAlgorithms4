import org.apache.spark._
import org.apache.spark.graphx._
import org.apache.spark.graphx.lib._
import org.apache.spark.graphx.util.GraphGenerators


/** Graph Generator. */
object SparkGraphGenerator {
	def main(args: Array[String]) {
		if (args.length < 2) {
			System.err.println("Usage: SparkGraphGenerator <vertex-count> <output-file>")
			System.exit(1)
		}
		val vertexCount = args(0).toInt
		val outputFile = args(1)

		val conf = new SparkConf()
		val sc = new SparkContext(conf.setAppName("SparkGraphGenerator(" + vertexCount + ")"))
		
		val graph = GraphGenerators.logNormalGraph(sc, numVertices = vertexCount)

		graph.edges.map(e => e.srcId + " " + e.dstId).saveAsTextFile(outputFile)
        sc.stop()
	}
}
