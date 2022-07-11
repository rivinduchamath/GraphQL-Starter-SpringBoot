/**
 * @author - Chamath_Wijayarathna
 * Date :7/11/2022
 */


package com.techprimers.graphql.springbootgrapqlexample.service.datafetcher;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.techprimers.graphql.springbootgrapqlexample.model.Book;
import com.techprimers.graphql.springbootgrapqlexample.service.GraphQLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BookSaveFetcher implements GraphQLMutationResolver {
    @Autowired
    GraphQLService service;

    public Book createBook(final String idNumber, final String title, final String publisher, final String[] authors, final String publishedDate) {
        return this.service.createBook(idNumber, title, publisher, authors,publishedDate);
    }
}