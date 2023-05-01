package com.pakjyu.springframework.beans.factory.factory;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.XmlUtil;
import com.pakjyu.springframework.beans.factory.BeansException;
import com.pakjyu.springframework.beans.factory.PropertyValue;
import com.pakjyu.springframework.beans.factory.PropertyValues;
import com.pakjyu.springframework.beans.factory.factory.dict.BeanXmlDict;
import com.pakjyu.springframework.beans.factory.factory.dict.BeanXmlDict.BeanPropertyXmlDict;
import com.pakjyu.springframework.beans.factory.support.AbstractBeanDefinitionReader;
import com.pakjyu.springframework.beans.factory.support.BeanDefinitionRegistry;
import com.pakjyu.springframework.io.Resource;
import com.pakjyu.springframework.io.ResourceLoader;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.io.InputStream;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public class XmlBeanDefinitionReader extends AbstractBeanDefinitionReader {

    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry) {
        super(registry);
    }

    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry, ResourceLoader resourceLoader) {
        super(registry, resourceLoader);
    }

    @Override
    public void loadBeanDefinitions(Resource resource) throws BeansException {

        try (InputStream inputStream = resource.getInputStream()) {
            doLoanBeanDefinition(inputStream);
        } catch (IOException | ClassNotFoundException e) {
            throw new BeansException("IOException parsing XML document from " + resource, e);
        }

    }

    @Override
    public void loadBeanDefinitions(Resource... resources) throws BeansException {
        for (Resource resource : resources) {
            loadBeanDefinitions(resource);
        }
    }

    @Override
    public void loadBeanDefinitions(String location) throws BeansException {
        Resource resource = getResourceLoader().getResource(location);
        loadBeanDefinitions(resource);
    }

    private void doLoanBeanDefinition(InputStream inputStream) throws ClassNotFoundException, BeansException {
        NodeList childNodes = XmlUtil.readXML(inputStream).getDocumentElement().getChildNodes();

        Predicate<Node> isInstanceOfElement = node -> node instanceof Element;
        BiPredicate<String, String> isEqual = (xmlDictName, nodeName) -> xmlDictName.equals(nodeName);

        for (int i = 0; i < childNodes.getLength(); i++) {

            if (!(isInstanceOfElement.test(childNodes.item(i)))) continue;
            if (!(isEqual.test(BeanXmlDict.NODENAME, childNodes.item(i).getNodeName()))) continue;

            Element xmlBean = (Element) childNodes.item(i);
            String xmlBeanId = xmlBean.getAttribute(BeanXmlDict.ID);
            String xmlBeanName = xmlBean.getAttribute(BeanXmlDict.NAME);
            String xmlBeanClassName = xmlBean.getAttribute(BeanXmlDict.CLASSNAME);

            Class<?> beanClass = Class.forName(xmlBeanClassName);
            String beanName;

            if (StrUtil.isEmpty(xmlBeanName)) {
                beanName = StrUtil.lowerFirst(beanClass.getSimpleName());
            } else {
                beanName = StrUtil.isNotEmpty(xmlBeanId) ? xmlBeanId : xmlBeanName;
            }

            BeanDefinition beanDefinition = new BeanDefinition(beanClass, new PropertyValues());

            for (int i1 = 0; i1 < xmlBean.getChildNodes().getLength(); i1++) {
                NodeList childNodes1 = xmlBean.getChildNodes();

                if (!(isInstanceOfElement.test(childNodes1.item(i1)))) continue;
                if (!(isEqual.test(BeanPropertyXmlDict.NODENAME, childNodes1.item(i).getNodeName()))) continue;

                Element property = (Element) childNodes1.item(i1);
                String attrName = property.getAttribute(BeanPropertyXmlDict.NAME);
                String attrValue = property.getAttribute(BeanPropertyXmlDict.VALUE);
                String attrRef = property.getAttribute(BeanPropertyXmlDict.REF);

                Object value = StrUtil.isNotEmpty(attrRef) ? new BeanReference(attrRef) : attrValue;
                beanDefinition.getPropertyValues().addPropertyValue(new PropertyValue(attrName, value));
            }

            if (getRegistry().containsBeanDefinition(beanName)) {
                throw new BeansException("Duplicate beanName[" + beanName + "] is not allowed");
            }

            getRegistry().registryBeanDefinition(beanName, beanDefinition);

        }
    }

    public void loadBeanDefinitions(String[] configLocations) {
        for (String configLocation : configLocations) {
            this.loadBeanDefinitions(configLocation);
        }
    }
}
