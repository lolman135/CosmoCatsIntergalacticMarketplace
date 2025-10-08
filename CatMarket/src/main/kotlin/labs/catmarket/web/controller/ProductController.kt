package labs.catmarket.web.controller

import jakarta.validation.Valid
import labs.catmarket.application.useCase.product.CreateProductUseCase
import labs.catmarket.application.useCase.product.DeleteProductByIdUseCase
import labs.catmarket.application.useCase.product.GetAllProductsUseCase
import labs.catmarket.application.useCase.product.GetProductByIdUseCase
import labs.catmarket.application.useCase.product.UpdateProductByIdUseCase
import labs.catmarket.infrastructure.dto.requet.busines.ProductDtoRequest
import labs.catmarket.infrastructure.dto.response.ProductDtoResponse
import labs.catmarket.infrastructure.mapper.ProductWebMapper
import labs.catmarket.infrastructure.mapper.ProductWebMapperHelper
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI
import java.util.UUID

@RestController
@RequestMapping("/api/v1/products")
@Validated
class ProductController(
    private val productWebMapper: ProductWebMapper,
    private val productWebMapperHelper: ProductWebMapperHelper,
    private val createProductUseCase: CreateProductUseCase,
    private val getAllProductsUseCase: GetAllProductsUseCase,
    private val getProductByIdUseCase: GetProductByIdUseCase,
    private val updateProductByIdUseCase: UpdateProductByIdUseCase,
    private val deleteProductByIdUseCase: DeleteProductByIdUseCase,
) {

    @PostMapping
    fun save(@RequestBody @Valid request: ProductDtoRequest): ResponseEntity<ProductDtoResponse> {
        val command = productWebMapper.toCommand(request)
        val response = productWebMapper.toDto(createProductUseCase.execute(command), productWebMapperHelper)
        val location = URI.create("/api/v1/categories/${response.id}")
        return ResponseEntity.created(location).body(response)
    }

    @GetMapping
    fun getAll() =
        ResponseEntity.ok(getAllProductsUseCase.execute(Unit).map {
            productWebMapper.toDto(it, productWebMapperHelper)
        })

    @GetMapping("/{id}")
    fun getById(@PathVariable id: UUID) =
        ResponseEntity.ok(productWebMapper.toDto(
            getProductByIdUseCase.execute(id),
            productWebMapperHelper)
        )

    @PutMapping("/{id}")
    fun updateById(
        @PathVariable id: UUID,
        @RequestBody @Valid request: ProductDtoRequest
    ): ResponseEntity<ProductDtoResponse> {
        val pair = id to productWebMapper.toCommand(request)
        val response = productWebMapper.toDto(updateProductByIdUseCase.execute(pair), productWebMapperHelper)
        return ResponseEntity.ok(response)
    }

    @DeleteMapping("/{id}")
    fun deleteById(@PathVariable id: UUID): ResponseEntity<Void> {
        deleteProductByIdUseCase.execute(id)
        return ResponseEntity.noContent().build()
    }
}