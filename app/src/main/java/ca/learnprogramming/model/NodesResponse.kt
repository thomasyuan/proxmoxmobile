// app/src/main/java/ca/learnprogramming/network/NodeStatusResponse.kt
package ca.learnprogramming.model

data class Node(
    val node: String,
    val status: String,
    val cpu: Double,
    val mem: Long,
    val maxcpu: Int,
    val maxmem: Long,
    val uptime: Long
)

data class NodesResponse(
    val data: List<Node>
)