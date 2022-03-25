package com.vadim.sneakerstore.dto.converter;

import com.vadim.sneakerstore.dto.ProductDto;
import com.vadim.sneakerstore.entity.*;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

@Component
public class ProductConverter {

    public Product convertToEntity(ProductDto productDto) {
        final UUID id = productDto.getId();
        final String name = productDto.getName();
        final String brand = productDto.getBrand();
        final String sex = productDto.getSex();
        final String destiny = productDto.getDestiny();
        final String season = productDto.getSeason();
        final String color = productDto.getColor();
        final String originCountry = productDto.getOriginCountry();
        final String description = productDto.getDescription();
        final String material = productDto.getMaterial();
        final BigDecimal price = productDto.getPrice();
        final List<Size> sizes;
        final List<Photo> photos;
        final List<Customer> customers;
        final List<Comment> comments;

        return Product.builder()
                .id(id)
                .price(price)
                .name(name)
                .season(season)
                .sex(sex)
                .description(description)
                .destiny(destiny)
                .material(material)
                .color(color)
                .brand(brand)
                .originCountry(originCountry)
                .build();
    }

    public ProductDto convertToDto(Product product) {
        final UUID id = product.getId();
        final BigDecimal price = product.getPrice();
        final String name = product.getName();
        final String brand = product.getBrand();
        final String sex = product.getSex();
        final String destiny = product.getDestiny();
        final String color = product.getColor();
        final String season = product.getSeason();
        final String originCountry = product.getOriginCountry();
        final String description = product.getDescription();
        final String material = product.getMaterial();

        Integer sum = product.getComments().stream()
                .mapToInt(Comment::getRate).sum();
        Integer count = product.getComments().size();
        final Double averageRate = (double) (sum / count);

        List<UUID> sizeIds = new ArrayList<>();
        if (Objects.nonNull(product.getSizes())) {
            sizeIds = product.getSizes().stream()
                    .map(Size::getId)
                    .collect(Collectors.toList());
        }

        List<UUID> photoIds = new ArrayList<>();
        if (Objects.nonNull(product.getPhotos())) {
             photoIds = product.getPhotos().stream()
                    .map(Photo::getId)
                     .collect(Collectors.toList());
        }

        List<UUID> customerIds = new ArrayList<>();
        if (Objects.nonNull(product.getCustomers())) {
            customerIds = product.getCustomers().stream()
                    .map(Customer::getId)
                    .collect(Collectors.toList());
        }

        List<UUID> commentIds = new ArrayList<>();
        if (Objects.nonNull(product.getComments())) {
            commentIds = product.getComments().stream()
                    .map(Comment::getId)
                    .collect(Collectors.toList());
        }

        return ProductDto.builder()
                .id(id)
                .price(price)
                .name(name)
                .season(season)
                .sex(sex)
                .description(description)
                .destiny(destiny)
                .material(material)
                .color(color)
                .brand(brand)
                .originCountry(originCountry)
                .commentIds(commentIds)
                .customerIds(customerIds)
                .photoIds(photoIds)
                .sizeIds(sizeIds)
                .averageRate(averageRate)
                .build();
    }
}
