package labs.catmarket.infrastructure.exception

import jakarta.servlet.http.HttpServletRequest
import labs.catmarket.infrastructure.dto.response.error.ErrorDtoResponse
import labs.catmarket.persistence.exception.EntityAlreadyExistsException
import labs.catmarket.persistence.exception.EntityNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException::class)
    fun handleEntityNotFoundException(
        ex: EntityNotFoundException,
        request: HttpServletRequest
    ): ResponseEntity<ErrorDtoResponse>{

        val error = ErrorDtoResponse(
            status = HttpStatus.NOT_FOUND.value(),
            error = "Nor Found",
            message = ex.message,
            path = request.servletPath
        )
        return ResponseEntity(error, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(EntityAlreadyExistsException::class)
    fun handleEntityAlreadyExistsException(
        ex: EntityAlreadyExistsException,
        request: HttpServletRequest
    ): ResponseEntity<ErrorDtoResponse>{

        val error = ErrorDtoResponse(
            status = HttpStatus.CONFLICT.value(),
            error = "Conflict",
            message = ex.message,
            path = request.servletPath
        )
        return ResponseEntity(error, HttpStatus.CONFLICT)
    }

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgumentException(
        ex: IllegalArgumentException,
        request: HttpServletRequest
    ): ResponseEntity<ErrorDtoResponse> {

        val error = ErrorDtoResponse(
            status = HttpStatus.BAD_REQUEST.value(),
            error = "Conflict",
            message = ex.message,
            path = request.servletPath
        )
        return ResponseEntity(error, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidation(ex: MethodArgumentNotValidException, request: HttpServletRequest): ResponseEntity<ErrorDtoResponse> {
        val errors = ex.bindingResult.fieldErrors
            .associate { it.field to (it.defaultMessage ?: "Invalid value") }

        val response = ErrorDtoResponse(
            status = HttpStatus.BAD_REQUEST.value(),
            error = "Bad Request",
            message = "Validation failed",
            errors = errors,
            path = request.servletPath
        )
        return ResponseEntity(response, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(Exception::class)
    fun handleAll(ex: Exception, request: HttpServletRequest): ResponseEntity<ErrorDtoResponse> {
        val response = ErrorDtoResponse(
            status = HttpStatus.INTERNAL_SERVER_ERROR.value(),
            error = "Internal Server Error",
            message = ex.message,
            path = request.servletPath
        )
        return ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR)
    }
}