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
    m.addAttribute("generateObj", g);
    return "generate";
  }

  // For fulfilling the action criteria - to receive the data.
  @GetMapping(path = "/generate")
  public String generate(@RequestParam Integer numberVal, Model m){
    // Pass in a model.
    this.randomizeNumber(m, numberVal.intValue());
    return "result";
  }

  private void randomizeNumber(Model m, int noOfGeneratedNo) {
    int maxGeneratedNo = 31;
    String[] imgNumbers = new String[maxGeneratedNo];

    // Check if the number is less than 1 || or more than maxGeneratedNo.
    if (noOfGeneratedNo < 1 || noOfGeneratedNo > maxGeneratedNo - 1) {
      throw new RandNoException();
    }

    // Generating number images' filename and setting it to the array. Holds all the filenames in String.
    for (int i = 0; i < maxGeneratedNo; i++) {
      if (i > 0) {
        System.out.print("i > " + i);
        imgNumbers[i] = "number" + i + ".jpg";
      }
    }

    List<String> selectedImages = new ArrayList<String>();
    Random rand = new Random();
    Set<Integer> uniqueResult = new LinkedHashSet<Integer>(); // This is where you store the numbers that have been chosen already. (use set, no duplicates).

    // Continue generating the next unique number, until it fulfills the condition -> desired number of numbers to generate.
    while (uniqueResult.size() < noOfGeneratedNo) { 
      Integer randNoResult = rand.nextInt(maxGeneratedNo);
      if (randNoResult != null) {
        uniqueResult.add(randNoResult);
      }
    }

    Integer currentElement = null;
    for (Iterator iter = uniqueResult.iterator(); iter.hasNext();) {
      currentElement = (Integer)iter.next();
      System.out.println(currentElement); // Might delete later.
      if (currentElement != null) {
        selectedImages.add(imgNumbers[currentElement]);
      }
    }

    // Page is expecting these two keys. Need to give it. (refer to html)
    m.addAttribute("noOfGeneratedNo", noOfGeneratedNo);
    m.addAttribute("selectedImgs", selectedImages);
    System.out.println(" >>> " + selectedImages);
    
    // m.addAttribute("sortedSelectedImgs", selectedImages.sort(null));


  }

}
