/*
 * [y] hybris Platform
 *
 * Copyright (c) 2017 SAP SE or an SAP affiliate company.  All rights reserved.
 *
 * This software is the confidential and proprietary information of SAP
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with SAP.
 */
package com.unbxd.recommendations.controllers.cms;

import com.unbxd.recommendations.enums.UnbxdPageType;
import com.unbxd.recommendations.model.UnbxdProductCarouselComponentModel;
import com.unbxd.recommendations.controllers.UnbxdRecommendationsControllerConstants;
import de.hybris.platform.acceleratorfacades.productcarousel.ProductCarouselFacade;
import de.hybris.platform.acceleratorservices.config.SiteConfigService;
import de.hybris.platform.addonsupport.controllers.cms.AbstractCMSAddOnComponentController;
import de.hybris.platform.commercefacades.order.CartFacade;
import de.hybris.platform.commercefacades.product.ProductOption;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commercefacades.search.ProductSearchFacade;
import de.hybris.platform.store.services.BaseStoreService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import de.hybris.platform.commercefacades.order.data.CartData;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


/**
 * Controller for CMS ProductReferencesComponent.
 */
@Controller("UnbxdProductCarouselComponentController")
@RequestMapping(value = UnbxdRecommendationsControllerConstants.Actions.Cms.UnbxdProductCarouselComponentController)
public class UnbxdProductCarouselComponentController extends AbstractCMSAddOnComponentController<UnbxdProductCarouselComponentModel>
{
	protected static final List<ProductOption> PRODUCT_OPTIONS = Arrays.asList(ProductOption.BASIC, ProductOption.PRICE);

	private static final String UNBXD_SITE_KEY = "unbxd.sitekey.";
	private static final String UNBXD_API_KEY = "unbxd.apikey.";

	private static final String UNBXD_SITEKEY_MODEL = "unbxdSiteKey";
	private static final String UNBXD_APIKEY_MODEL = "unbxdApiKey";

	@Resource(name = "productSearchFacade")
	private ProductSearchFacade<ProductData> productSearchFacade;

	@Resource(name = "productCarouselFacade")
	private ProductCarouselFacade productCarouselFacade;

	@Resource(name = "siteConfigService")
	private SiteConfigService siteConfigService;

	@Resource(name = "baseStoreService")
	private BaseStoreService baseStoreService;

	@Resource(name = "cartFacade")
	private CartFacade cartFacade;

	@Override
	protected void fillModel(final HttpServletRequest request, final Model model, final UnbxdProductCarouselComponentModel component)
	{
		final List<ProductData> products = new ArrayList<>();

		products.addAll(collectRecommendedProducts(component));

		model.addAttribute("title", component.getTitle());
		model.addAttribute("productData", products);
		model.addAttribute("pageType", component.getPageType().getCode());

		if(getCartFacade().hasEntries()){
			CartData cartData = getCartFacade().getSessionCart();
			model.addAttribute("cartentries",cartData.getEntries());
			List<String> productcodes = new ArrayList<>();
			cartData.getEntries().forEach(entry -> productcodes.add(entry.getProduct().getCode()));
			model.addAttribute("productcodes",productcodes);
		}

		model.addAttribute(UNBXD_SITEKEY_MODEL, getCurrentUnbxdAutoSuggestSiteKey());
		model.addAttribute(UNBXD_APIKEY_MODEL, getCurrentUnbxdAutoSuggestAPIKey());


	}

	protected String getCurrentUnbxdAutoSuggestSiteKey()
	{
		return siteConfigService.getProperty(UNBXD_SITE_KEY + baseStoreService.getCurrentBaseStore().getUid());
	}

	protected String getCurrentUnbxdAutoSuggestAPIKey()
	{
		return siteConfigService.getProperty(UNBXD_API_KEY + baseStoreService.getCurrentBaseStore().getUid());
	}

	protected List<ProductData> collectRecommendedProducts(final UnbxdProductCarouselComponentModel component)
	{
//		final SearchQueryData searchQueryData = new SearchQueryData();
//		searchQueryData.setValue(component.getSearchQuery());
//		final String categoryCode = component.getCategoryCode();
//
//		if (searchQueryData.getValue() != null && categoryCode != null)
//		{
//			final SearchStateData searchState = new SearchStateData();
//			searchState.setQuery(searchQueryData);
//
//			final PageableData pageableData = new PageableData();
//			pageableData.setPageSize(100); // Limit to 100 matching results
//
//			return productSearchFacade.categorySearch(categoryCode, searchState, pageableData).getResults();
//		}

		return Collections.emptyList();
	}

	protected CartFacade getCartFacade()
	{
		return cartFacade;
	}
}
