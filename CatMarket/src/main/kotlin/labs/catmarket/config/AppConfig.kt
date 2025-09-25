package labs.catmarket.config

import labs.catmarket.application.usecase.category.CreateCategoryUsecase
import labs.catmarket.application.usecase.category.DeleteCategoryByIdUsecase
import labs.catmarket.application.usecase.category.GetAllCategoriesUsecase
import labs.catmarket.application.usecase.category.GetCategoryByIdUsecase
import labs.catmarket.application.usecase.category.UpdateCategoryByIdUsecase
import labs.catmarket.application.usecase.product.CreateProductUsecase
import labs.catmarket.domain.category.CategoryRepository
import labs.catmarket.domain.product.ProductRepository
import labs.catmarket.persistence.category.CategoryMockRepository
import labs.catmarket.persistence.product.ProductMockRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.UUID

@Configuration
class AppConfig {

    //Category
    @Bean
    fun categoryRepository(): CategoryRepository = CategoryMockRepository()

    @Bean
    fun createCategoryUsecase(categoryRepository: CategoryRepository): CreateCategoryUsecase =
        CreateCategoryUsecase(categoryRepository)

    @Bean
    fun getCategoryByIdUsecase(categoryRepository: CategoryRepository): GetCategoryByIdUsecase =
        GetCategoryByIdUsecase(categoryRepository)

    @Bean
    fun getAllCategoriesUsecase(categoryRepository: CategoryRepository): GetAllCategoriesUsecase =
        GetAllCategoriesUsecase(categoryRepository)

    @Bean
    fun updateCategoryByIdUsecase(categoryRepository: CategoryRepository): UpdateCategoryByIdUsecase =
        UpdateCategoryByIdUsecase(categoryRepository)

    @Bean
    fun deleteCategoryByIdUsecase(categoryRepository: CategoryRepository): DeleteCategoryByIdUsecase =
        DeleteCategoryByIdUsecase(categoryRepository)

    //Product
    @Bean
    fun productRepository(): ProductRepository = ProductMockRepository()

    @Bean
    fun createProductUsecase(productRepository: ProductRepository): CreateProductUsecase =
        CreateProductUsecase(productRepository)
}