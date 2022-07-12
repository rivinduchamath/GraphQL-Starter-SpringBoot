package crud.resolver;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;

import crud.entity.Category;
import crud.entity.Product;
import crud.repository.CategoryRepository;
import crud.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MutationResolver implements GraphQLMutationResolver {

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private ProductRepository productRepository;

	public Category addCategory(String name) {
		Category category = new Category();
		category.setName(name);

		return categoryRepository.saveAndFlush(category);
	}

	public Product addProduct(String name, String code, Double price, Integer category_id) {
		Category category = categoryRepository.findById(category_id).orElseGet(null);

		Product product = new Product();
		product.setName(name);
		product.setCode(code);
		product.setPrice(price);
		product.setCategory(category);

		return productRepository.saveAndFlush(product);
	}

	public Category updateCategory(Integer id, String name) {
		Category category = new Category();
		category.setId(id);
		category.setName(name);

		return categoryRepository.saveAndFlush(category);
	}

	public Product updateProduct(Integer id, String name, String code, Double price, Integer category_id) {
		Category category = categoryRepository.findById(category_id).orElseGet(null);
		
		Product product = new Product();
		product.setId(id);
		product.setName(name);
		product.setCode(code);
		product.setPrice(price);
		product.setCategory(category);

		return productRepository.saveAndFlush(product);
	}

	public Boolean deleteCategory(Integer id) {
		categoryRepository.deleteById(id);
		return true;
	}

	public Boolean deleteProduct(Integer id) {
		productRepository.deleteById(id);
		return true;
	}

}
