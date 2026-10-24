package com.velora.api.controller;
import com.velora.api.repository.StoreContentRepository;import org.springframework.web.bind.annotation.*;import java.util.*;
@RestController public class ContentController {private final StoreContentRepository repository;public ContentController(StoreContentRepository r){repository=r;}@GetMapping("/api/content") public Map<String,String> content(){Map<String,String> m=new HashMap<>();repository.findAll().forEach(c->m.put(c.getContentKey(),c.getContentValue()));return m;}}
