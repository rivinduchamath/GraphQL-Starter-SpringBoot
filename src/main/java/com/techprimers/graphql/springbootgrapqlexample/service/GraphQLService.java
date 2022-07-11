package com.techprimers.graphql.springbootgrapqlexample.service;

import com.techprimers.graphql.springbootgrapqlexample.model.Book;
import com.techprimers.graphql.springbootgrapqlexample.repository.BookRepository;
import com.techprimers.graphql.springbootgrapqlexample.service.datafetcher.AllBooksDataFetcher;
import com.techprimers.graphql.springbootgrapqlexample.service.datafetcher.BookDataFetcher;
import com.techprimers.graphql.springbootgrapqlexample.service.datafetcher.BookSaveFetcher;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.stream.Stream;

@Service
public class GraphQLService {

    @Autowired
    BookRepository bookRepository;

    @Value("classpath:books.graphql") // Inject Location of graphql Location In the resource File
    Resource resource;

    private GraphQL graphQL; // We can Execute any Query With using this api
    @Autowired
    private AllBooksDataFetcher allBooksDataFetcher;
    @Autowired
    private BookDataFetcher bookDataFetcher;


    // load schema at application start up
    @PostConstruct
    private void loadSchema() throws IOException {

        //Load Books into the Book Repository
        loadDataIntoHSQL();

        // get the schema
        File schemaFile = resource.getFile();
        // parse schema
        TypeDefinitionRegistry typeRegistry = new SchemaParser().parse(schemaFile);
        RuntimeWiring wiring = buildRuntimeWiring();
        GraphQLSchema schema = new SchemaGenerator().makeExecutableSchema(typeRegistry, wiring);
        graphQL = GraphQL.newGraphQL(schema).build();
    }
    private RuntimeWiring buildRuntimeWiring() { // Execute the USer Selected Data (Required data) From books.graphql Type Query
        return RuntimeWiring.newRuntimeWiring()
                // What type of wiring we need
                .type("Query", typeWiring -> typeWiring
                        .dataFetcher("allBooks", allBooksDataFetcher)
                        .dataFetcher("book", bookDataFetcher)
                )
                .build();
    }

    private void loadDataIntoHSQL() {

        Stream.of(
                new Book("123", "Book of Clouds", "Kindle Edition",
                        new String[] {
                        "Chloe Aridjis"
                        }, "Nov 2017"),
                new Book("124", "Cloud Arch & Engineering", "Orielly",
                        new String[] {
                                "Peter", "Sam"
                        }, "Jan 2015"),
                new Book("125", "Java 9 Programming", "Orielly",
                        new String[] {
                                "Venkat", "Ram"
                        }, "Dec 2016")
        ).forEach(book -> {
            bookRepository.save(book);
        });
    }


    public GraphQL getGraphQL() {
        return graphQL;
    }

    @Transactional
    public Book createBook(String idNumber, String title, String publisher, String[] authors, String publishedDate) {
        final Book vehicle = new Book();
        vehicle.setIdNumber(idNumber);
        vehicle.setTitle(title);
        vehicle.setPublisher(publisher);
        vehicle.setAuthors(authors);
        vehicle.setPublishedDate(publishedDate);
        return this.bookRepository.save(vehicle);
    }

}
