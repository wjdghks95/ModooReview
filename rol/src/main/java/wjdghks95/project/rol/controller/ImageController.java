package wjdghks95.project.rol.controller;

import lombok.RequiredArgsConstructor;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import wjdghks95.project.rol.domain.FileStore;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

@RestController
@RequiredArgsConstructor
public class ImageController {

    private final FileStore fileStore;

    @Value("classpath:${resource.path.profile.defaultImg}")
    private Resource resource;

    @GetMapping("/images/{filename}")
    public Resource downloadImage(@PathVariable String filename) throws IOException {
        return new UrlResource("file:" + fileStore.createPath(filename));
    }

    @GetMapping("/images/profile")
    public Resource profile() throws IOException {
        return new UrlResource(resource.getURI());
    }
}
