package com.vadim.sneakerstore.dto.converter;

import com.vadim.sneakerstore.dto.ProductDto;
import com.vadim.sneakerstore.entity.*;
import com.vadim.sneakerstore.repository.PictureRepository;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class ProductConverter {

    private final PictureRepository pictureRepository;

    public ProductConverter(PictureRepository pictureRepository) {
        this.pictureRepository = pictureRepository;
    }

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
        final Double price = productDto.getPrice();

        List<Picture> photos = new ArrayList<>();
        if (Objects.nonNull(productDto.getPhotos())) {
            photos = pictureRepository.findAllById(productDto.getPhotos());
        }

//        List<Picture> pictures = new ArrayList<>();
//        if (Objects.nonNull(productDto.getPhotos())) {
//            pictures = pictureRepository.findAllById(productDto.getPhotos());
//        }

        List<Size> sizes = new ArrayList<>();
        List<Customer> customers;
        List<Comment> comments;

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
                .pictures(photos)
                .originCountry(originCountry)
                .build();
    }

    public ProductDto convertToDto(Product product) {
        final UUID id = product.getId();
        final Double price = product.getPrice();
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

        List<String> links = new ArrayList<>();
        if (Objects.nonNull(product.getPictures())) {
             links = product.getPictures().stream()
                    .map(Picture::getLink)
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
                .photos(links)
                .sizesIds(sizesIds)
                .averageRate(averageRate)
                .build();
    }
}
