package nehe.demo;

import nehe.demo.Modals.Product;
import nehe.demo.Modals.User;
import nehe.demo.Repositories.ProductRepository;
import nehe.demo.Services.ProductService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import static  org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.booleanThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;


/**
 * ProductServiceTests
 */
@RunWith(SpringRunner.class)
public class ProductServiceTests {

    @MockBean
    private ProductRepository repository;

    @Autowired
    private ProductService productService;

    @Test
    public void test() {

        Mockito.when(repository.findByName("ARVs")).thenReturn(new Product());

        boolean result = productService.checkIfProductExists("ARVs");

        assertEquals(true, result);
    }

    @TestConfiguration
    static class TestConfig {

        @Bean
        public ProductService productService(final ProductRepository repository) {

            return new ProductService(repository);
        }
    }

    
}