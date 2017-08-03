/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;

import javax.validation.constraints.AssertFalse;
import javax.validation.constraints.AssertTrue;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Matti
 */
public class ImageFile {

    @NotEmpty(message = "Title must not be empty")
    private String title;
    private String description;
    private MultipartFile file;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    @AssertFalse(message = "File cannot be empty")
    public boolean isEmpty() {
        return file.isEmpty();
    }

    @AssertFalse(message = "File size must be less than 5MB")
    public boolean isOversized() {
        return file.getSize() > (5 * 1024 * 1024);
    }

    @AssertTrue(message = "Only image files allowed")
    public boolean isImageFile() {
        return file.getContentType().startsWith("image/");
    }
}
