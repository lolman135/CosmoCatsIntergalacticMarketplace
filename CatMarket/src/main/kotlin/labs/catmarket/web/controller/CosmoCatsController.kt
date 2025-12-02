package labs.catmarket.web.controller

import labs.catmarket.application.useCase.cosmoCats.GetCosmoCatsUseCase
import labs.catmarket.featuretoggle.FeatureToggles
import labs.catmarket.featuretoggle.annotation.FeatureToggle
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/cosmo-cats")
class CosmoCatsController(private val getCosmoCatsUseCase: GetCosmoCatsUseCase) {

    @GetMapping
    @FeatureToggle(FeatureToggles.COSMO_CATS)
    fun getCosmoCats(): ResponseEntity<String>{
        return ResponseEntity.ok(getCosmoCatsUseCase.execute(Unit))
    }
}