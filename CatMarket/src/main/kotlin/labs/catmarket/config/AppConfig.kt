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
import labs.catmarket.repository.category.CategoryRepository
import labs.catmarket.repository.order.OrderRepository
import labs.catmarket.repository.product.ProductRepository
import labs.catmarket.common.CartStorage
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

    //========================================Product==========================================
    @Bean
    fun productRepository(): ProductRepository = ProductMockRepository()

    //========================================Order==========================================

    @Bean
    fun orderRepository(): OrderRepository = OrderMockRepository()

}