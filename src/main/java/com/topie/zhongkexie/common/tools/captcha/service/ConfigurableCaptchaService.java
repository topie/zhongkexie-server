/*
 * Copyright (c) 2009 Piotr Piastucki
 * 
 * This file is part of Patchca CAPTCHA library.
 * 
 *  Patchca is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Lesser General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *  
 *  Patchca is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser General Public License for more details.
 *  
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with Patchca. If not, see <http://www.gnu.org/licenses/>.
 */
package com.topie.zhongkexie.common.tools.captcha.service;

import com.topie.zhongkexie.common.tools.captcha.background.SingleColorBackgroundFactory;
import com.topie.zhongkexie.common.tools.captcha.color.SingleColorFactory;
import com.topie.zhongkexie.common.tools.captcha.filter.predefined.CurvesRippleFilterFactory;
import com.topie.zhongkexie.common.tools.captcha.font.RandomFontFactory;
import com.topie.zhongkexie.common.tools.captcha.renderer.BestFitTextRenderer;
import com.topie.zhongkexie.common.tools.captcha.word.RandomWordFactory;

public class ConfigurableCaptchaService extends AbstractCaptchaService {

    public ConfigurableCaptchaService() {
        backgroundFactory = new SingleColorBackgroundFactory();
        wordFactory = new RandomWordFactory();
        fontFactory = new RandomFontFactory();
        textRenderer = new BestFitTextRenderer();
        colorFactory = new SingleColorFactory();
        filterFactory = new CurvesRippleFilterFactory(colorFactory);
        textRenderer.setLeftMargin(10);
        textRenderer.setRightMargin(10);
        width = 160;
        height = 70;
    }

}
