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

        List<UUID> sizesIds = new ArrayList<>();
        if (Objects.nonNull(product.getSizes())) {
            sizesIds = product.getSizes().stream()
                    .map(Size::getId)
                    .collect(Collectors.toList());
        }

        List<UUID> photosIds = new ArrayList<>();
        if (Objects.nonNull(product.getPhotos())) {
             photosIds = product.getPhotos().stream()
                    .map(Photo::getId)
                     .collect(Collectors.toList());
        }

//        List<UUID> customersIds = new ArrayList<>();
//        if (Objects.nonNull(product.getCustomers())) {
//            customersIds = product.getCustomers().stream()
//                    .map(Customer::getId)
//                    .collect(Collectors.toList());
//        }

        double averageRate = 0D;
        List<UUID> commentIds = new ArrayList<>();
        if (Objects.nonNull(product.getComments())) {
            commentIds = product.getComments().stream()
                    .map(Comment::getId)
                    .collect(Collectors.toList());
            Integer sum = product.getComments().stream()
                    .mapToInt(Comment::getRate).sum();

            if (!sum.equals(0)) {
                int count = product.getComments().size();
                averageRate = (double) sum / count;
            }
        }

        List<UUID> customersFavoritesIds = new ArrayList<>();
        if (Objects.nonNull(product.getCustomers())) {
            customersFavoritesIds = product.getCustomers().stream()
                    .map(Customer::getId)
                    .collect(Collectors.toList());
        }

        List<UUID> inCustomersCartsIds = new ArrayList<>();
        if (Objects.nonNull(product.getInCustomersCarts())) {
            inCustomersCartsIds = product.getInCustomersCarts().stream()
                    .map(Customer::getId)
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
                .commentsIds(commentIds)
                .customersIds(customersFavoritesIds)
                .inCustomersCarts(inCustomersCartsIds)
                .photosIds(photosIds)
                .sizesIds(sizesIds)
                .averageRate(averageRate)
                .build();
    }
}
