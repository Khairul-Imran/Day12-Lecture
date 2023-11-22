package sg.edu.nus.iss.Day12Lecture.RandomNumberGenerator.controller;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import java.util.Iterator;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import sg.edu.nus.iss.Day12Lecture.RandomNumberGenerator.Model.Generate;
import sg.edu.nus.iss.Day12Lecture.RandomNumberGenerator.exception.RandNoException;

@Controller
@RequestMapping(path = "/rand")
public class GenerateRandController {
  
  @GetMapping(path = "/show")
  public String showRandomForm(Model m) {
    Generate g = new Generate();
    m.addAttribute("generateObject", g);
    return "generate";
  }

  // For fulfilling the action criteria - to receive the data.
  @GetMapping(path = "/generate")
  public String generate(@RequestParam Integer numberValue, Model m){
    // Pass in a model.
    this.randomizeNumber(m, numberValue.intValue());
    return "result";
  }

  private void randomizeNumber(Model m, int numberOfNumsToGenerate) {
    int generatedNumberLimit = 31;
    String[] imageNumbers = new String[generatedNumberLimit];

    List<String> selectedImages = new ArrayList<String>();
    Random rand = new Random();
    Set<Integer> uniqueResult = new LinkedHashSet<Integer>(); // This is where you store the unique numbers that were chosen.

    // Check if the number is less than 1 || or more than generatedNumberLimit.
    if (numberOfNumsToGenerate < 1 || numberOfNumsToGenerate > generatedNumberLimit - 1) {
      throw new RandNoException();
    }

    // Generating number images' filename and placing it inside the array.
    for (int i = 0; i < generatedNumberLimit; i++) {
      if (i > 0) {
        System.out.print("i > " + i);
        imageNumbers[i] = "number" + i + ".jpg";
      }
    }

    // Continue generating the next unique number, until it fulfills the condition -> desired number of numbers to generate.
    while (uniqueResult.size() < numberOfNumsToGenerate) { 
      Integer randomNumberResult = rand.nextInt(generatedNumberLimit);
      if (randomNumberResult != null) {
        uniqueResult.add(randomNumberResult);
      }
    }

    Integer currentElement = null;
    for (Iterator iter = uniqueResult.iterator(); iter.hasNext();) {
      currentElement = (Integer)iter.next();
      System.out.println(currentElement); // Might delete later.
      if (currentElement != null) {
        selectedImages.add(imageNumbers[currentElement]);
      }
    }

    // Page is expecting these two keys. Need to give it. (refer to html)
    m.addAttribute("numberOfNumsToGenerate", numberOfNumsToGenerate);
    m.addAttribute("selectedImages", selectedImages);
    System.out.println(" >>> " + selectedImages);
    
    // m.addAttribute("sortedSelectedImgs", selectedImages.sort(null));

  }
}
