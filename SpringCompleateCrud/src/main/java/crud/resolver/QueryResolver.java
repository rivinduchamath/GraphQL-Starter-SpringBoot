package crud.resolver;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;

import crud.entity.Category;
import crud.entity.Product;
import crud.repository.CategoryRepository;
import crud.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class QueryResolver implements GraphQLQueryResolver {

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private ProductRepository productRepository;

	public List<Category> allCategories() {
		return categoryRepository.findAll();
	}

	public List<Product> allProducts() {
		return productRepository.findAll();
	}

	public Category category(Integer id) {
		return categoryRepository.findById(id).orElseGet(null);
	}

	public Product product(Integer id) {
		return productRepository.findById(id).orElseGet(null);
	}

}
