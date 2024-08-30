package ca.learnprogramming.model

data class NodeStatus(
    val cpu: Double,
    val mem: Long,
    val disk: Long
)

data class NodeStatusResponse(
    val data: NodeStatus
)
