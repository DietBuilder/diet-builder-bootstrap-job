package com.dietbuilder.core.services;

import com.dietbuilder.domain.daos.ComestibleProductDao;
import com.dietbuilder.domain.daos.RecipeDao;
import com.dietbuilder.domain.model.comestible_product.ComestibleProduct;
import com.dietbuilder.domain.model.recipe.Recipe;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class InitializeDatabaseService {

    @Autowired
    private ComestibleProductDao comestibleProductDao;

    @Autowired
    private RecipeDao recipeDao;

    @Autowired
    private LoadComestibleProductsFromXlsx loadComestibleProductListfromXlsxFile;

    @Autowired
    private LoadRecipesForXlsx loadRecipesForXlsx;

    public void initializeComestibleProducts() {
        List<ComestibleProduct> comestibleProducts = this.loadComestibleProductListfromXlsxFile.loadFromXlsxFile();
        this.comestibleProductDao.saveAll(comestibleProducts);
        log.info("comestibleproducts table has been initialized");
    }

    public void initializeMeals() {
        List<Recipe> meals = loadRecipesForXlsx.loadFromXlsxFile();
        this.recipeDao.saveAll(meals);
        log.info("meals table has been initialized");
    }

    public void dropDatabase() {
        this.recipeDao.deleteAll();
        this.comestibleProductDao.deleteAll();
        log.info("Database has been dropped");
    }
}
