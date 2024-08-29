// app/src/main/java/ca/learnprogramming/network/NodeStatusResponse.kt
package ca.learnprogramming.network

data class Node(
    val node: String,
    val status: String,
    val maxcpu: Int,
    val maxmem: Long,
    val maxdisk: Long
)

data class NodesResponse(
    val data: List<Node>
)

data class NodeStatus(
    val cpu: Double,
    val mem: Long,
    val disk: Long
)

data class NodeStatusResponse(
    val data: NodeStatus
)
