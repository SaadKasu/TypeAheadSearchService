package com.saadkasu.type_ahead_search_service;

import com.saadkasu.type_ahead_search_service.Client.TypeAheadSearchServiceApplication;
import com.saadkasu.type_ahead_search_service.Controllers.ISearchTermController;
import com.saadkasu.type_ahead_search_service.Controllers.SearchTermController;
import com.saadkasu.type_ahead_search_service.DTOs.SearchResponseDTO;
import com.saadkasu.type_ahead_search_service.Services.ISearchTermService;
import com.saadkasu.type_ahead_search_service.Services.SearchTermService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
class TypeAheadSearchServiceApplicationTests {

}
