package controller;

import java.util.Collection;
import java.util.Iterator;

import ioc.Container;
import ioc.ContainerXML;
import ioc.IBean;
import model.Gallery;
import model.Image;

public class Controller {
	
	public static void main(String[] args) {
		Container container = new ContainerXML();
		
		container.register(Image.class);
		
		container.loadFrom("dataImage.xml");
		
		@SuppressWarnings("unchecked")
		IBean<Image> beanFactory = container.get(Image.class);
		
		Collection<Image> collect = beanFactory.listAll();
		
		Gallery gallery = new Gallery((Collection<Image>) collect);
		
		Iterator<Image> it = gallery.getImages();
		
		while (it.hasNext()){
			Image i = it.next();
			System.out.println(i.getPath());
		}
		
		GalleryController gc = new GalleryController(gallery);
		
	}

}
