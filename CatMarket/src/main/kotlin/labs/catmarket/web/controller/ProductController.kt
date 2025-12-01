package labs.catmarket.web.controller

import jakarta.validation.Valid
import labs.catmarket.application.useCase.product.CreateProductUseCase
import labs.catmarket.application.useCase.product.DeleteProductByIdUseCase
import labs.catmarket.application.useCase.product.GetAllProductsUseCase
import labs.catmarket.application.useCase.product.GetProductByIdUseCase
import labs.catmarket.application.useCase.product.GetProductProjectionByNameUseCase
import labs.catmarket.application.useCase.product.UpdateProductByIdUseCase
import labs.catmarket.dto.inbound.ProductDtoInbound
import labs.catmarket.dto.outbound.ProductDtoOutbound
import labs.catmarket.dto.outbound.ProductProjectionDtoOutbound
import labs.catmarket.mapper.ProductMapper
import labs.catmarket.mapper.ProductMapperHelper
import labs.catmarket.repository.projection.ProductDetailsProjection
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import java.net.URI
import java.util.UUID

@RestController
@RequestMapping("/api/v1/products")
@Validated
class ProductController(
    private val productMapper: ProductMapper,
    private val productMapperHelper: ProductMapperHelper,
    private val createProductUseCase: CreateProductUseCase,
    private val getAllProductsUseCase: GetAllProductsUseCase,
    private val getProductByIdUseCase: GetProductByIdUseCase,
    private val updateProductByIdUseCase: UpdateProductByIdUseCase,
    private val deleteProductByIdUseCase: DeleteProductByIdUseCase,
    private val getProductProjectionByNameUseCase: GetProductProjectionByNameUseCase
) {

    @PostMapping
    fun save(@RequestBody @Valid request: ProductDtoInbound): ResponseEntity<ProductDtoOutbound> {
        val command = productMapper.toCommand(request)
        val outbound = productMapper.toDto(createProductUseCase.execute(command), productMapperHelper)
        val location = URI.create("/api/v1/categories/${outbound.id}")
        return ResponseEntity.created(location).body(outbound)
    }

    @GetMapping
    fun getAll() =
        ResponseEntity.ok(getAllProductsUseCase.execute(Unit).map {
            productMapper.toDto(it, productMapperHelper)
        })

    @GetMapping("/projections")
    fun getProjectionByName(@RequestParam name: String): ResponseEntity<ProductProjectionDtoOutbound> {
        return ResponseEntity.ok(getProductProjectionByNameUseCase.execute(name))
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: UUID) =
        ResponseEntity.ok(productMapper.toDto(
            getProductByIdUseCase.execute(id),
            productMapperHelper)
        )

    @PutMapping("/{id}")
    fun updateById(
        @PathVariable id: UUID,
        @RequestBody @Valid request: ProductDtoInbound
    ): ResponseEntity<ProductDtoOutbound> {
        val pair = id to productMapper.toCommand(request)
        val outbound = productMapper.toDto(updateProductByIdUseCase.execute(pair), productMapperHelper)
        return ResponseEntity.ok(outbound)
    }

    @DeleteMapping("/{id}")
    fun deleteById(@PathVariable id: UUID): ResponseEntity<Void> {
        deleteProductByIdUseCase.execute(id)
        return ResponseEntity.noContent().build()
    }
}