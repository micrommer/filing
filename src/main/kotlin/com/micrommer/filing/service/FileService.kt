package com.micrommer.filing.service

import com.micrommer.filing.domain.FileDetail
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import java.nio.channels.FileChannel
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.stream.Stream

/**
 * Ashiyane: filing (com.micrommer.filing.service)
 * @author : imanbhlool
 * @since : Dec/21/2020 - 2:51 PM, Monday
 */
@Service
class FileService {

    @Value("\${filing.path.root}")
    private lateinit var fileDirectory: String

    private val pwd = Paths.get("").toAbsolutePath()

    private val absoluteFileDirectory by lazy {
        Path.of(pwd.toString() + fileDirectory)
    }

    fun getFileDetail(name: String): Mono<FileDetail> {
        return Flux.fromStream(provideStream()).filter { path ->
            path.fileName.toString() == name
        }.map { path ->
            val size = FileChannel.open(path).size()
            val fileName = path.fileName.toString()
            FileDetail(fileName, size)
        }.toMono()
    }

    fun getFiles(): Flux<FileDetail> {
        return Flux.fromStream(provideStream()).map { path ->
            val size = FileChannel.open(path).size()
            val fileName = path.fileName.toString()
            FileDetail(fileName, size)
        }
    }

    /**
     * Provide stream : Regenerate file's path stream by call
     *
     * @return Stream<Path>
     */
    private fun provideStream() : Stream<Path> = Files.list(absoluteFileDirectory).filter { path ->
        !path.fileName.toString().startsWith(".")
    }
}