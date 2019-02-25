/**
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 2019-01-09.
 *
 * @author Thomas Couchoud
 * @since 2019-01-09
 */
open module fr.mrcraftcod.simulator {
	requires annotations;
	
	requires org.slf4j;
	requires org.apache.logging.log4j;
	requires jcommander;
	
	requires java.scripting;
	requires javafx.graphics;
	requires javafx.controls;
	requires javafx.swing;
	
	exports fr.mrcraftcod.shcheduler.jfx to javafx.graphics;
	exports fr.mrcraftcod.shcheduler.model;
}