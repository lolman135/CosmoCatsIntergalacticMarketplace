package labs.catmarket.config

import labs.catmarket.application.useCase.category.*
import labs.catmarket.application.useCase.product.*
import labs.catmarket.domain.category.CategoryRepository
import labs.catmarket.domain.product.ProductRepository
import labs.catmarket.persistence.category.CategoryMockRepository
import labs.catmarket.persistence.product.ProductMockRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AppConfig {

    //=====================================Category=======================================
    @Bean
    fun categoryRepository(): CategoryRepository = CategoryMockRepository()

    @Bean
    fun createCategoryUseCase(categoryRepository: CategoryRepository) = CreateCategoryUseCase(categoryRepository)

    @Bean
    fun getCategoryByIdUseCase(categoryRepository: CategoryRepository) = GetCategoryByIdUseCase(categoryRepository)

    @Bean
    fun getAllCategoriesUseCase(categoryRepository: CategoryRepository) = GetAllCategoriesUseCase(categoryRepository)

    @Bean
    fun updateCategoryByIdUseCase(categoryRepository: CategoryRepository) = UpdateCategoryByIdUseCase(categoryRepository)

    @Bean
    fun deleteCategoryByIdUseCase(categoryRepository: CategoryRepository) = DeleteCategoryByIdUseCase(categoryRepository)


    //========================================Product==========================================
    @Bean
    fun productRepository(): ProductRepository = ProductMockRepository()

    @Bean
    fun createProductUseCase(
        productRepository: ProductRepository,
        categoryRepository: CategoryRepository
    ) = CreateProductUseCase(productRepository, categoryRepository)

    @Bean
    fun getProductByIdUseCase(productRepository: ProductRepository) = GetProductByIdUseCase(productRepository)

    @Bean
    fun getAllProductsUseCase(productRepository: ProductRepository) = GetAllProductsUseCase(productRepository)

    @Bean
    fun updateProductByIdUseCase(
        productRepository: ProductRepository,
        categoryRepository: CategoryRepository
    ) = UpdateProductByIdUseCase(productRepository, categoryRepository)

    @Bean
    fun deleteProductByIdUseCase(productRepository: ProductRepository) = DeleteProductByIdUseCase(productRepository)
}