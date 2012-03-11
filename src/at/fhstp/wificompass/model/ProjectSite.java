/*
 * Created on Dec 8, 2011
 * Author: Paul Woelfel
 * Email: frig@frig.at
 */
package at.fhstp.wificompass.model;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.w3c.dom.Element;
import org.xmlpull.v1.XmlSerializer;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import at.fhstp.wificompass.interfaces.XMLSerializable;
import at.fhstp.wificompass.model.xml.XMLSettings;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = ProjectSite.TABLE_NAME)
public class ProjectSite implements XMLSerializable {
	
	public static final String TABLE_NAME="sites";

	@DatabaseField(generatedId = true)
	protected int id;

	@DatabaseField
	protected String title;
	
	@DatabaseField
	protected String description;
	
	@DatabaseField(dataType = DataType.BYTE_ARRAY)
	protected byte[] background;
	
	protected Bitmap backgroundBitmap;
	
	@DatabaseField
	protected int width;
	
	@DatabaseField
	protected int height;
	
	@DatabaseField(dataType = DataType.BYTE_ARRAY)
	protected byte[] image;
	
	protected Bitmap imageBitmap;
	
	protected static final int quality=100;
	
	@DatabaseField(foreign = true,foreignAutoRefresh = true)
	protected Project project;

	@ForeignCollectionField
	protected ForeignCollection<AccessPoint> accessPoints;
	
	@ForeignCollectionField
	protected ForeignCollection<WifiScanResult> scanResults;
	
	@DatabaseField(foreign = true,foreignAutoRefresh = true)
	protected Location lastLocation;
	
	
	protected static final String XMLTAG = "location", XMLTITLE = "title";
	
	public static final String UNTITLED="untitled";
	
	public static String FIELD_PROJECT_FK=Project.TABLE_NAME+"_"+Project.FIELD_ID;

	public ProjectSite() {
		this(null,null);
	}

	public ProjectSite(String title) {
		this(null,title);
	}
	
	public ProjectSite(Project project){
		this(project,null);
	}
	
	public ProjectSite(Project project,String title){
		this.title= title;
		this.project=project;
		if(this.title==null){
			this.title=UNTITLED;
		}
		width=0;
		height=0;
	}
	
	
	@Override
	public void serialize(XmlSerializer serializer) throws RuntimeException, IOException {
		serializer.startTag(XMLSettings.XMLNS, XMLTAG);

		serializer.startTag(XMLSettings.XMLNS, XMLTITLE).text(title).endTag(XMLSettings.XMLNS, XMLTITLE);

		serializer.endTag(XMLSettings.XMLNS, XMLTITLE);
	}

	@Override
	public void deserialize(Element e) {
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Bitmap getBackgroundBitmap() {
		if(backgroundBitmap==null&&background!=null){
			backgroundBitmap=BitmapFactory.decodeByteArray(background, 0, background.length);
		}
		return backgroundBitmap;
	}

	public boolean setBackgroundBitmap(Bitmap backgroundBitmap) {
		
		ByteArrayOutputStream baos=new ByteArrayOutputStream();
		if(backgroundBitmap.compress(Bitmap.CompressFormat.PNG, quality, baos)){
			background=baos.toByteArray();
			this.backgroundBitmap = backgroundBitmap;
			return true;
		}
		return false;
	}

	public Bitmap getImageBitmap() {
		if(imageBitmap==null&&image!=null){
			imageBitmap=BitmapFactory.decodeByteArray(image, 0, image.length);
		}
		return imageBitmap;
	}

	public boolean setImageBitmap(Bitmap imageBitmap) {
		ByteArrayOutputStream baos=new ByteArrayOutputStream();
		if(imageBitmap.compress(Bitmap.CompressFormat.PNG, quality, baos)){
			image=baos.toByteArray();
			this.imageBitmap = imageBitmap;
			return true;
		}
		return false;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public int getId() {
		return id;
	}

	/**
	 * @return the accessPoints
	 */
	public ForeignCollection<AccessPoint> getAccessPoints() {
		return accessPoints;
	}

	/**
	 * @return the scanResults
	 */
	public ForeignCollection<WifiScanResult> getScanResults() {
		return scanResults;
	}

	/**
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}
	
	/**
	 * set the size of the project site
	 * @param width
	 * @param height
	 */
	public void setSize(int width,int height){
		this.width=width;
		this.height=height;
	}

	/**
	 * @return the last known Location
	 */
	public Location getLastLocation() {
		return lastLocation;
	}

	/**
	 * @param lastLocation the lastLocation to set
	 */
	public void setLastLocation(Location lastLocation) {
		this.lastLocation = lastLocation;
	}

}
