package com.soodagram.soodagram;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.soodagram.soodagram.domain.repository.FeedRepository;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles(value = "dev")
public class FeedRepositoryTest {
	
	@Autowired
	private FeedRepository feedRepository;
	
	@Test
	public void testFeedWrite() {
		
	}
	
}
