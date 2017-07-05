/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dz.analyzers;

import java.io.*;
import java.nio.charset.Charset;

import org.supercsv.io.*;
import org.supercsv.prefs.CsvPreference;

/**
 *
 * @author David
 */
public class ReviewsCSV {
    
    
    public static class ReviewsRow {
        // Refer only to columns that are relevant for task
        private String id;
        private String productId;
        private String profileName;
        private String text;

        public ReviewsRow() {
        }
        
        public String getId() {
            return id;
        }

        public String getProductId() {
            return productId;
        }

        public String getProfileName() {
            return profileName;
        }

        public String getText() {
            return text;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public void setProfileName(String profileName) {
            this.profileName = profileName;
        }

        public void setText(String text) {
            this.text = text;
        }
           
    }
   
    public void initialize (String path1, String path2) throws IOException, Throwable {
        try {
            try {
                inpStrm = new FileInputStream (path1);
                if (inpStrm==null) {
                   throw new IOException ("Could not create FileInputStream.") ; 
                }
            } catch (IOException e) {
                    inpStrm = new FileInputStream (path2);
                    if (inpStrm==null) {
                      throw new Exception ("Could not create FileInputStream.") ; 
                   }
            }
            inpStrmRdr = new InputStreamReader(inpStrm,Charset.forName("UTF8"));
            if (inpStrmRdr==null) {
               throw new Exception ("Could not create InputStreamReader.") ; 
            }
           // Note that if used STANDARD_PREFERENCE, if rewriting csv,
            // both c/r and l/f characters would be written at end of line.
            csvReader = new CsvBeanReader(inpStrmRdr, CsvPreference.EXCEL_PREFERENCE);
            if (inpStrmRdr==null) {
               throw new Exception ("Could not create CsvBeanReader.") ; 
            }
            // Skip first header line.
            String[] dummyHeader = csvReader.getHeader(true);
            if (dummyHeader==null) {
                throw new Exception ("Could not skip header row of csv file.");
            }
            
        } catch (IOException e) {
            System.out.println ("An IO exception occured when trying to initialise ReviewsCSV for csv file: "+path2) ;
            System.out.println ("The exception is: "+ e.getLocalizedMessage());
            throw e; //Propogate exception 
        } catch (Throwable t) { // Catch other exceptions - such as unrecognized charset 
            System.out.println ("An exception occured when trying to initialise ReviewsCSV for csv file: "+path2) ;
            System.out.println ("The exception is: "+ t.getLocalizedMessage());
            throw t; //Propogate exception 
           
        }
    } 
     public void closeAll () throws IOException, Throwable {
        try {
            if (csvReader!=null) {
                csvReader.close();
            }
            if (inpStrmRdr!=null) {
                inpStrmRdr.close();
            }
            if (inpStrm!=null) {
                inpStrm.close();
            }
            
         } catch (IOException e) {
            System.out.println ("An IO exception occured when trying to close ReviewsCSV.") ;
            System.out.println ("The exception is: "+ e.getLocalizedMessage());
            return; //Do not propogate exception 
        } catch (Throwable t) { // Catch other exceptions - such as unrecognized charset 
            System.out.println ("An exception occured when trying to close ReviewsCSV.") ;
            System.out.println ("The exception is: "+ t.getLocalizedMessage());
            return; //Do not ropogate exception 
           
        }
    }
   
    public ReviewsRow nextRow() throws IOException,Throwable {
        try {
            reviewsRow = csvReader.read(ReviewsRow.class,reviewsRowNameMapping);
            return reviewsRow;
        } catch (IOException e) {
            System.out.println ("An IO exception occured when trying to read ReviewsCSV.") ;
            System.out.println ("The exception is: "+ e.getLocalizedMessage());
            throw e; //Propogate exception 
        } catch (Throwable t) { // Catch other exceptions - such as unrecognized charset 
            System.out.println ("An exception occured when trying to read ReviewsCSV.") ;
            System.out.println ("The exception is: "+ t.getLocalizedMessage());
            throw t; //Propogate exception 
           
        }
    }
    public ReviewsRow getRow() throws Throwable {
        return reviewsRow;
    }
    private CsvBeanReader csvReader = null;   
    private Reader inpStrmRdr = null ;

    private InputStream inpStrm = null;
    
    private ReviewsRow reviewsRow = null;
    private final String[] reviewsRowNameMapping = new String[] {"id","productId",null,"profileName",
        null,null,null,null,null,"text"};
        
    
}
