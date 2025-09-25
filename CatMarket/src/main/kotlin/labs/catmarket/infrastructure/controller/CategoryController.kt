package labs.catmarket.infrastructure.controller

import labs.catmarket.application.usecase.category.CreateCategoryUsecase
import labs.catmarket.application.usecase.category.DeleteCategoryByIdUsecase
import labs.catmarket.application.usecase.category.GetAllCategoriesUsecase
import labs.catmarket.application.usecase.category.GetCategoryByIdUsecase
import labs.catmarket.application.usecase.category.UpdateCategoryByIdUsecase
import labs.catmarket.infrastructure.dto.requet.busines.CategoryDtoRequest
import labs.catmarket.infrastructure.dto.response.busines.CategoryDtoResponse
import labs.catmarket.infrastructure.mapper.CategoryWebMapper
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI
import java.util.UUID

@RestController
@RequestMapping("/api/v1/categories")
class CategoryController(
    private val categoryWebMapper: CategoryWebMapper,
    private val createCategoryUsecase: CreateCategoryUsecase,
    private val getCategoryByIdUsecase: GetCategoryByIdUsecase,
    private val getAllCategoriesUsecase: GetAllCategoriesUsecase,
    private val updateCategoryByIdUsecase: UpdateCategoryByIdUsecase,
    private val deleteCategoryByIdUsecase: DeleteCategoryByIdUsecase
) {

    @PostMapping
    fun save(@RequestBody request: CategoryDtoRequest): ResponseEntity<CategoryDtoResponse>{
        val domainCategory = categoryWebMapper.toDomain(request)
        val response = categoryWebMapper.toDto(createCategoryUsecase.execute(domainCategory))
        val location = URI.create("/api/v1/categories/${response.id}")
        return ResponseEntity.created(location).body(response)
    }

    @GetMapping
    fun getAll() = ResponseEntity.ok(getAllCategoriesUsecase.execute())

    @GetMapping("/{id}")
    fun getById(@PathVariable id: UUID) =
         ResponseEntity.ok(categoryWebMapper.toDto(getCategoryByIdUsecase.execute(id)))

    @PutMapping("/{id}")
    fun updateById(
        @PathVariable id: UUID,
        @RequestBody request: CategoryDtoRequest
    ): ResponseEntity<CategoryDtoResponse> {
        val domainCategory = categoryWebMapper.toDomain(request)
        val response = categoryWebMapper.toDto(updateCategoryByIdUsecase.execute(id,domainCategory))
        return ResponseEntity.ok(response)
    }

    @DeleteMapping("/{id}")
    fun deleteById(@PathVariable id: UUID): ResponseEntity<Void>{
        deleteCategoryByIdUsecase.execute(id)
        return ResponseEntity.noContent().build()
    }
}