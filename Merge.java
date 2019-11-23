import java.io.*;
import java.util.*;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.*;
import java.nio.charset.StandardCharsets;

public class Merge
{
  public static void RecursivePrint(File[] arr,String pbi, String pbo, Integer max_size,String dfpath)
  {
  	 int i=1;
     JSONArray merge_json = new JSONArray();
     String key="";
     for (File f : arr)
     {
       if(f.isFile())
			 {
				 if(f.getName().equals(pbi+Integer.toString(i)+".json"))
				 {
            try (FileReader reader = new FileReader(f.getPath()))
            {
                //Read JSON file
                Object obj = new JSONParser().parse(reader);
                JSONObject jsonList = (JSONObject) obj;
                Iterator<String> keysItr = jsonList.keySet().iterator();
                key = keysItr.next();
                JSONArray strikers = (JSONArray) jsonList.get(key);
                Iterator<JSONObject> it = strikers.iterator();
            		while (it.hasNext()) {
                  JSONObject jsonObject = it.next();
                  merge_json.add(jsonObject);
            		}
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
					// System.out.println("processing the file "+f.getName());
				 }
				 i++;
			 }
       else if(f.isDirectory())
       {
         System.out.println("Expected Files only!!Not considered in further processing.");
       }
     }
     generateFile(merge_json, key, pbo, max_size,dfpath);
  }

  public static void generateFile(JSONArray json_objects, String key, String pbo, Integer max_size,String dfpath) {
    int size = 0;
    int index = 1;
    JSONObject sb = new JSONObject();
    JSONObject tmp_merge_object = new JSONObject();
    sb.put(key, new JSONArray());
    tmp_merge_object.put(key, new JSONArray());
    for (Object object : json_objects) {
      JSONArray merge_array = (JSONArray) sb.get(key);
      JSONArray tmp_merge_array = (JSONArray) tmp_merge_object.get(key);
      tmp_merge_array.add((JSONObject) object);
      size = tmp_merge_object.toJSONString().getBytes(StandardCharsets.UTF_8).length;
      if (size > max_size) {
        String fileName = dfpath+"/" + pbo + index + ".json";
        //System.out.println(""+size + " "+ (size > max_size) + " "+ fileName);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
          writer.write(sb.toJSONString());
        } catch (IOException ex) {
          ex.printStackTrace();
        }
        index++;
        sb = new JSONObject();
        tmp_merge_object = new JSONObject();
        JSONArray new_json = new JSONArray();
        new_json.add(object);
        JSONArray tmp_json = new JSONArray();
        tmp_json.add(object);
        sb.put(key, new_json);
        tmp_merge_object.put(key, tmp_json);
        size = sb.toJSONString().getBytes(StandardCharsets.UTF_8).length;
      } else {
        merge_array.add((JSONObject) object);
      }
    }
    // for cases where we don't reach the limit
    String fileName = dfpath+"/" + pbo + index + ".json";
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
      writer.write(sb.toJSONString());
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  public static void main(String[] args)
  {
    System.out.println("Enter the source file path:");
    Scanner scanner = new Scanner(System.in);
    String sfpath=scanner.nextLine();

    System.out.println("Enter the destination file path:");
    String dfpath=scanner.nextLine();

    System.out.println("Enter the prefix base name for the input files:");
    String pbi=scanner.nextLine();

    System.out.println("Enter the prefix base name for the output files:");
    String pbo=scanner.nextLine();

    System.out.println("Enter the maxFileSize for the output:(Integer Expected)");
    int maxFileSize=scanner.nextInt();

    File maindir = new File(sfpath);

    if(maindir.exists() && maindir.isDirectory())
    {
      File arr[] = maindir.listFiles();

      System.out.println("**********************************************");
      System.out.println("Files from main directory : " + maindir);
      System.out.println("**********************************************");
      RecursivePrint(arr,pbi,pbo,maxFileSize,dfpath);
    }
  }
}
