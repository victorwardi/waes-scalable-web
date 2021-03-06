package com.rsouza01.waesscalableweb.util.json;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rsouza01.waesscalableweb.enums.JsonContentsResult;

/**
 * 
 * @author Rodrigo Souza (rsouza01)
 *
 */
public class JsonContentsComparator {

	/** Instance logger */
	private Logger logger = LoggerFactory.getLogger(JsonContentsComparator.class);

	/** Left panel content */
	private String leftJsonContent; 

	/** Right panel content */
	private String rightJsonContent; 

	/**
	 * 
	 * @param leftJsonContent Left panel content
	 * @param rightJsonContent Right panel content
	 */
	public JsonContentsComparator(String leftJsonContent, String rightJsonContent) {
		super();
		this.leftJsonContent = leftJsonContent;
		this.rightJsonContent = rightJsonContent;
	}

	/**
	 * This method compares the contents of the two JSON files provided via constructor.
	 * @return {@code JsonContentsComparison}
	 */
	public JsonContentsComparison compare() {
		
		if((leftJsonContent == null && rightJsonContent == null) ||
				leftJsonContent.equals(rightJsonContent)) {
			
			logger.debug("No differences found.");
			
			return new JsonContentsComparison(JsonContentsResult.EQUAL_CONTENTS);
		} 

		if(leftJsonContent != null && rightJsonContent != null 
				&& leftJsonContent.length() == rightJsonContent.length()
				&& !leftJsonContent.equals(rightJsonContent)) {

			logger.debug("Inputs have the same size but different contents.");
			
			return new JsonContentsComparison(JsonContentsResult.SAME_SIZES_BUT_DIFFERENT_CONTENTS);
		}

		/** Here, we are aware that the contents are different. 
		 * I am computing what those differences are. */
		
		JsonObject leftJsonObject = new JsonObject(leftJsonContent);
		JsonObject rightJsonObject = new JsonObject(rightJsonContent);

		List<JsonObjectDifference> leftDifferences = 
				leftJsonObject.getDifferencesWith(rightJsonObject);
		
		List<JsonObjectDifference> rightDifferences = 
				rightJsonObject.getDifferencesWith(leftJsonObject);

		return new JsonContentsComparison(
				JsonContentsResult.DIFFERENT_SIZES_CONTENTS, 
				leftDifferences, 
				rightDifferences);
	}
}
