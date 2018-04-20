package com.meraj.microservices.reviewservice.Controller;

import com.meraj.microservices.reviewservice.model.Review;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.Path;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/review")
public class ReviewController {
    @RequestMapping(method = RequestMethod.GET, value="{productId}")
    public List<Review> getReviews(
            @PathVariable(value = "productId", required = true) String productId) {
        List<Review> list = new ArrayList<>();
        list.add(new Review(productId, 1, "Author 1", "Subject 1", "Content 1"));
        list.add(new Review(productId, 2, "Author 2", "Subject 2", "Content 2"));
        list.add(new Review(productId, 3, "Author 3", "Subject 3", "Content 3"));

        return list;
    }
}
