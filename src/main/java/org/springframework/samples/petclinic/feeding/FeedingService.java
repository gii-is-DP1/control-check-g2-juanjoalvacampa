package org.springframework.samples.petclinic.feeding;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeedingService {
	private FeedingRepository feedingRepository;
	
	@Autowired
	public FeedingService(FeedingRepository feedingRepository) {
		this.feedingRepository = feedingRepository;
	}
	
    public List<Feeding> getAll(){
        return this.feedingRepository.findAll();
    }

    public List<FeedingType> getAllFeedingTypes(){
        return null;
    }

    public FeedingType getFeedingType(String typeName) {
        return this.feedingRepository.getFeedingType(typeName);
    }

    @Transactional
    public void save(Feeding feeding) throws UnfeasibleFeedingException {

        if(feeding.getPet().getType().getName().equals(feeding.getFeedingType().getName())) {
        	feedingRepository.save(feeding);
        }else {
            throw new UnfeasibleFeedingException();
        }

    }

    
}
