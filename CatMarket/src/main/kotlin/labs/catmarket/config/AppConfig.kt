package labs.catmarket.config

import labs.catmarket.application.useCase.order.CreateOrderUseCase
import labs.catmarket.application.useCase.cart.AddProductToCartUseCase
import labs.catmarket.application.useCase.cart.CleanCartForUserUseCase
import labs.catmarket.application.useCase.cart.GetCartByUserIdUseCase
import labs.catmarket.application.useCase.category.*
import labs.catmarket.application.useCase.order.DeleteOrderByIdUseCase
import labs.catmarket.application.useCase.order.GetAllOrdersUseCase
import labs.catmarket.application.useCase.order.GetOrderByIdUseCase
import labs.catmarket.application.useCase.product.*
import labs.catmarket.domain.cart.CartStorage
import labs.catmarket.domain.category.CategoryRepository
import labs.catmarket.domain.order.OrderRepository
import labs.catmarket.domain.product.ProductRepository
import labs.catmarket.common.CartStorageImpl
import labs.catmarket.repository.category.CategoryMockRepository
import labs.catmarket.repository.order.OrderMockRepository
import labs.catmarket.repository.product.ProductMockRepository
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

    //========================================Cart==========================================

    @Bean
    fun cartStorage(): CartStorage = CartStorageImpl()

    @Bean
    fun addProductToCartUseCase(
        cartStorage: CartStorage,
        productRepository: ProductRepository
    ) = AddProductToCartUseCase(cartStorage, productRepository)

    @Bean
    fun getCartByUserIdUseCase(cartStorage: CartStorage) = GetCartByUserIdUseCase(cartStorage)

    @Bean
    fun cleanCartForUserUseCase(cartStorage: CartStorage) = CleanCartForUserUseCase(cartStorage)

    //========================================Order==========================================

    @Bean
    fun orderRepository(): OrderRepository = OrderMockRepository()

    @Bean
    fun createOrderUseCase(
        orderRepository: OrderRepository,
        productRepository: ProductRepository,
        cartStorage: CartStorage
    ) = CreateOrderUseCase(orderRepository, productRepository, cartStorage)

    @Bean
    fun getAllOrdersUseCase(orderRepository: OrderRepository) = GetAllOrdersUseCase(orderRepository)

    @Bean
    fun getOrderByIdUseCase(orderRepository: OrderRepository) = GetOrderByIdUseCase(orderRepository)

    @Bean
    fun deleteOrderByIdUseCase(orderRepository: OrderRepository) = DeleteOrderByIdUseCase(orderRepository)
}