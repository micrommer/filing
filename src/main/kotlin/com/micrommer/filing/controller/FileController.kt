package com.micrommer.filing.controller

import com.micrommer.filing.domain.FileDetail
import com.micrommer.filing.service.FileService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

/**
 * Ashiyane: filing (com.micrommer.filing.controller)
 * @author : imanbhlool
 * @since : Dec/21/2020 - 2:33 PM, Monday
 */
@RestController
@RequestMapping("/file")
class FileController(private val fileService: FileService) {

    @GetMapping("/{fileName}")
    fun getFile(@PathVariable fileName: String): Mono<FileDetail> {
        return fileService.getFileDetail(fileName)
    }

    @GetMapping("/files")
    fun getFiles() : Flux<FileDetail>{
        return fileService.getFiles()
    }
}