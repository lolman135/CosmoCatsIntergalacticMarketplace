package labs.catmarket.config

import labs.catmarket.repository.impl.NaturalIdRepositoryImpl
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@Configuration
@EnableJpaAuditing
@EnableJpaRepositories(
    basePackages = ["labs.catmarket.repository"],
    repositoryBaseClass = NaturalIdRepositoryImpl::class)
class JpaRepositoryConfig