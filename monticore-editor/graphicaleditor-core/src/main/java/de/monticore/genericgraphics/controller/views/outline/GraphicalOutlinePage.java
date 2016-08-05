/*******************************************************************************
 * MontiCore Language Workbench
 * Copyright (c) 2015, 2016, MontiCore, All rights reserved.
 *  
 * This project is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3.0 of the License, or (at your option) any later version.
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this project. If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/
package de.monticore.genericgraphics.controller.views.outline;

import org.eclipse.core.resources.IFile;
import org.eclipse.draw2d.LightweightSystem;
import org.eclipse.draw2d.Viewport;
import org.eclipse.draw2d.parts.ScrollableThumbnail;
import org.eclipse.gef.DefaultEditDomain;
import org.eclipse.gef.LayerConstants;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.editparts.ScalableFreeformRootEditPart;
import org.eclipse.gef.editparts.ScalableRootEditPart;
import org.eclipse.gef.ui.parts.ContentOutlinePage;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.part.IPageSite;

import de.monticore.editorconnector.menus.OutlineMenuContribution;
import de.monticore.genericgraphics.GenericGraphicsViewer;

/**
 * Depending on the {@link GenericGraphicsViewer} assigned in the constructor, this
 * ContentOutlinePage either displays a graphical viewer or an overview of that viewer. If the
 * viewer's control has been created already (which means it has already been added to an editor for
 * example), a thumbnail of the viewer is displayed as an overview. Otherwise, the viewer's control
 * is created in {@link #createControl(Composite)} and the viewer gets configured, initialized and
 * displayed. <br>
 * 
 * @author Philipp Kehrbusch
 */
public class GraphicalOutlinePage extends ContentOutlinePage {
  
  private GenericGraphicsViewer viewer;
  
  private DefaultEditDomain editDomain;
  
  private ScrollableThumbnail thumbnail;
  
  private DisposeListener disposeListener;
  
  private LightweightSystem lws;
  
  private SashForm sash;
  
  private Canvas canvas;
  
  public GraphicalOutlinePage(GenericGraphicsViewer viewer, IFile input) {
    super(viewer);
    this.viewer = viewer;
    editDomain = new DefaultEditDomain(null);
  }
  
  @Override
  public void createControl(Composite parent) {
    if (viewer != null) {
      createOverview(parent);
      createPopupMenu(canvas);
    }
  }
  
  /**
   * Assigns a model file to this OutlinePage's viewer
   * 
   * @param file File containing the model data
   */
  public void setInputFile(IFile file) {
    if (viewer != null) {
      viewer.setInput(file);
    }
  }
  
  private void createOverview(Composite parent) {
    sash = new SashForm(parent, SWT.VERTICAL);
    canvas = new Canvas(sash, SWT.NONE);
    lws = new LightweightSystem(canvas);
    
    refreshContents();
  }
  
  private void refreshContents() {
    if (viewer != null) {
      // miniature view
      if (viewer.getRootEditPart() instanceof ScalableRootEditPart) {
        thumbnail = new ScrollableThumbnail(
            (Viewport) ((ScalableRootEditPart) viewer.getRootEditPart()).getFigure());
        thumbnail.setSource(((ScalableRootEditPart) viewer.getRootEditPart())
            .getLayer(LayerConstants.PRINTABLE_LAYERS));
      }
      else if (viewer.getRootEditPart() instanceof ScalableFreeformRootEditPart) {
        thumbnail = new ScrollableThumbnail(
            (Viewport) ((ScalableFreeformRootEditPart) viewer.getRootEditPart()).getFigure());
        thumbnail.setSource(((ScalableFreeformRootEditPart) viewer.getRootEditPart())
            .getLayer(LayerConstants.PRINTABLE_LAYERS));
      }
      
      lws.setContents(thumbnail);
      disposeListener = new DisposeListener() {
        
        @Override
        public void widgetDisposed(DisposeEvent e) {
          if (thumbnail != null) {
            thumbnail.deactivate();
            thumbnail = null;
          }
        }
      };
      viewer.getControl().addDisposeListener(disposeListener);
    }
  }
  
  /**
   * Creates the context menu, attaches it to a specified control and registers it to the
   * {@link IPageSite}.
   * 
   * @param control The {@link Control}
   */
  private void createPopupMenu(Control control) {
    MenuManager manager = new MenuManager();
    
    if (control != null) {
      Menu context = manager.createContextMenu(control);
      control.setMenu(context);
      getSite().registerContextMenu("outlineGraphicalContext", manager, this);
      // add menu for selecting default outline type
      manager.add(new OutlineMenuContribution(false));
      manager.add(new OutlineMenuContribution(true));
    }
  }
  
  @Override
  public Control getControl() {
    return sash;
  }
  
  @Override
  public void dispose() {
    if (viewer != null) {
      if (viewer.getControl() != null && !viewer.getControl().isDisposed()) {
        if (disposeListener != null) {
          viewer.getControl().removeDisposeListener(disposeListener);
        }
      }
      
      viewer.dispose();
    }
    super.dispose();
  }
  
  @Override
  public void setFocus() {
    if (sash != null) {
      sash.setFocus();
    }
  }
  
  public GenericGraphicsViewer getViewer() {
    return viewer;
  }
  
  public CommandStack getCommandStack() {
    if (editDomain != null) {
      return editDomain.getCommandStack();
    } else {
      return null;
    }
  }
  
}