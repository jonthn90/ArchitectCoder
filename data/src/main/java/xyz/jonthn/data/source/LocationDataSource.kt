package xyz.jonthn.data.source

interface LocationDataSource {
    suspend fun findLastRegion(): String?
}