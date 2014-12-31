/*
   Copyright (c) 2014 Ahome' Innovation Technologies. All rights reserved.

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */

package com.ait.lienzo.client.core.shape;

import com.ait.lienzo.client.core.Context2D;
import com.ait.lienzo.client.core.shape.json.validators.ValidationContext;
import com.ait.lienzo.client.core.shape.json.validators.ValidationException;
import com.ait.lienzo.client.core.types.BoundingBox;
import com.ait.lienzo.client.core.types.NFastArrayList;
import com.ait.lienzo.client.core.types.PathPartList;
import com.ait.lienzo.shared.core.types.ShapeType;
import com.google.gwt.json.client.JSONObject;

public abstract class MultiPathPartShape<T extends MultiPathPartShape<T>> extends Shape<T>
{
    private final NFastArrayList<PathPartList> m_list = new NFastArrayList<PathPartList>();

    protected MultiPathPartShape(ShapeType type)
    {
        super(type);
    }

    protected MultiPathPartShape(ShapeType type, JSONObject node, ValidationContext ctx) throws ValidationException
    {
        super(type, node, ctx);
    }

    @Override
    public BoundingBox getBoundingBox()
    {
        final int size = m_list.size();

        if (size < 1)
        {
            return new BoundingBox(0, 0, 0, 0);
        }
        BoundingBox bbox = new BoundingBox();

        for (int i = 0; i < size; i++)
        {
            bbox.add(m_list.get(i).getBoundingBox());
        }
        return bbox;
    }

    protected T addPathPartList(PathPartList list)
    {
        m_list.add(list);

        return cast();
    }

    protected NFastArrayList<PathPartList> getPathPartListArray()
    {
        return m_list;
    }

    @Override
    protected void drawWithoutTransforms(Context2D context, double alpha)
    {
        Attributes attr = getAttributes();

        alpha = alpha * attr.getAlpha();

        if (alpha <= 0)
        {
            return;
        }
        if (prepare(context, attr, alpha))
        {
            final int size = m_list.size();

            if (size < 1)
            {
                return;
            }
            for (int i = 0; i < size; i++)
            {
                setAppliedShadow(false);

                setWasFilledFlag(false);

                PathPartList list = m_list.get(i);

                if (list.size() > 1)
                {
                    if (context.path(list))
                    {
                        fill(context, attr, alpha);
                    }
                    stroke(context, attr, alpha);
                }
            }
        }
    }
}
