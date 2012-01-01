/*
 * =============================================================================
 * 
 *   Copyright (c) 2011, The THYMELEAF team (http://www.thymeleaf.org)
 * 
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 * 
 *       http://www.apache.org/licenses/LICENSE-2.0
 * 
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 * 
 * =============================================================================
 */
package org.thymeleaf.processor.tag;

import java.util.List;

import org.thymeleaf.Arguments;
import org.thymeleaf.dom.NestableNode;
import org.thymeleaf.dom.Node;
import org.thymeleaf.dom.Tag;
import org.thymeleaf.inliner.ITextInliner;
import org.thymeleaf.processor.ITagNameProcessorMatcher;
import org.thymeleaf.processor.ProcessorResult;

/**
 * 
 * @author Daniel Fern&aacute;ndez
 * 
 * @since 1.0
 *
 */
public abstract class AbstractTextInlinerTagProcessor 
        extends AbstractTagProcessor {
    
    
    

    public AbstractTextInlinerTagProcessor(final String tagName) {
        super(tagName);
    }
    
    public AbstractTextInlinerTagProcessor(final ITagNameProcessorMatcher matcher) {
        super(matcher);
    }


    
    
    @Override
    public final ProcessorResult processTag(final Arguments arguments, final Tag tag) {

        
        final ITextInliner textInliner = 
            getTextInliner(arguments, tag);
        
        final boolean removeHostTag =
            removeHostTag(arguments, tag);
        
        clearSkippability(tag);
        
        if (removeHostTag) {
            tag.getParent().extractChild(tag);
        }
        
        return ProcessorResult.setInliner(textInliner);
        
    }
    
    
    protected abstract ITextInliner getTextInliner(final Arguments arguments, final Tag tag);

 
    
    protected abstract boolean removeHostTag(final Arguments arguments, final Tag tag);


    
    
    private static void clearSkippability(final Node node) {
        if (node != null) {
            node.setSkippable(false);
            if (node instanceof NestableNode) {
                final List<Node> children = ((NestableNode)node).getChildren();
                for (final Node child : children) {
                    clearSkippability(child);
                }
            }
        }
    }
    
}
