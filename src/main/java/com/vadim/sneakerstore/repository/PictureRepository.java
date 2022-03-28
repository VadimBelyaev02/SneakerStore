package com.vadim.sneakerstore.repository;

import com.vadim.sneakerstore.entity.Picture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PictureRepository extends JpaRepository<Picture, UUID> {

}
