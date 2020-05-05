package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.support.SessionStatus;

import javax.validation.Valid;
import org.springframework.data.domain.Pageable;
import java.util.ArrayList;
import java.util.List;

@Controller
@SessionAttributes(types = {DemoForm.class, SearchForm.class})
public class DemoController {

    /**
     * Demo�T�[�r�X�N���X�ւ̃A�N�Z�X
     */
    @Autowired
    private DemoService demoService;

    /**
     * ���[�U�[�f�[�^�e�[�u��(user_data)�̃f�[�^���擾���ĕԋp����
     * @return ���[�U�[�f�[�^���X�g
     */
    @ModelAttribute("demoFormList")
    public List<DemoForm> userDataList(){
        List<DemoForm> demoFormList = new ArrayList<>();
        return demoFormList;
    }

    /**
     * �ǉ��E�X�V�pForm�I�u�W�F�N�g�����������ĕԋp����
     * @return �ǉ��E�X�V�pForm�I�u�W�F�N�g
     */
    @ModelAttribute("demoForm")
    public DemoForm createDemoForm(){
        DemoForm demoForm = new DemoForm();
        return demoForm;
    }

    /**
     * �����pForm�I�u�W�F�N�g�����������ĕԋp����
     * @return �����pForm�I�u�W�F�N�g
     */
    @ModelAttribute("searchForm")
    public SearchForm createSearchForm(){
        SearchForm searchForm = new SearchForm();
        return searchForm;
    }

    /**
     * �����\��(����)��ʂɑJ�ڂ���
     * @return ������ʂւ̃p�X
     */
    @RequestMapping("/")
    public String index(){
        return "search";
    }

    /**
     * �����������s���A�ꗗ��ʂɑJ�ڂ���
     * @param searchForm �����pForm�I�u�W�F�N�g
     * @param model Model�I�u�W�F�N�g
     * @param result �o�C���h����
     * @return �ꗗ��ʂւ̃p�X
     */
    @RequestMapping("/search")
    public String search(SearchForm searchForm, Model model, BindingResult result){
        //�����pForm�I�u�W�F�N�g�̃`�F�b�N����
        String returnVal = demoService.checkSearchForm(searchForm, result);
        if(returnVal != null){
            return returnVal;
        }
        //���݃y�[�W����1�y�[�W�ڂɐݒ肵�A�ꗗ��ʂɑJ�ڂ���
        searchForm.setCurrentPageNum(1);
        return movePageInList(model, searchForm);
    }

    /**
     * �ꗗ��ʂŁu�擪�ցv�����N�������Ɏ��y�[�W��\������
     * @param searchForm �����pForm�I�u�W�F�N�g
     * @param model Model�I�u�W�F�N�g
     * @return �ꗗ��ʂւ̃p�X
     */
    @RequestMapping("/firstPage")
    public String firstPage(SearchForm searchForm, Model model){
        //���݃y�[�W����擪�y�[�W�ɐݒ肷��
        searchForm.setCurrentPageNum(1);
        return movePageInList(model, searchForm);
    }

    /**
     * �ꗗ��ʂŁu�O�ցv�����N�������Ɏ��y�[�W��\������
     * @param searchForm �����pForm�I�u�W�F�N�g
     * @param model Model�I�u�W�F�N�g
     * @return �ꗗ��ʂւ̃p�X
     */
    @RequestMapping("/backPage")
    public String backPage(SearchForm searchForm, Model model){
        //���݃y�[�W����O�y�[�W�ɐݒ肷��
        searchForm.setCurrentPageNum(searchForm.getCurrentPageNum() - 1);
        return movePageInList(model, searchForm);
    }

    /**
     * �ꗗ��ʂŁu���ցv�����N�������Ɏ��y�[�W��\������
     * @param searchForm �����pForm�I�u�W�F�N�g
     * @param model Model�I�u�W�F�N�g
     * @return �ꗗ��ʂւ̃p�X
     */
    @RequestMapping("/nextPage")
    public String nextPage(SearchForm searchForm, Model model){
        //���݃y�[�W�������y�[�W�ɐݒ肷��
        searchForm.setCurrentPageNum(searchForm.getCurrentPageNum() + 1);
        return movePageInList(model, searchForm);
    }

    /**
     * �ꗗ��ʂŁu�Ō�ցv�����N�������Ɏ��y�[�W��\������
     * @param searchForm �����pForm�I�u�W�F�N�g
     * @param model Model�I�u�W�F�N�g
     * @return �ꗗ��ʂւ̃p�X
     */
    @RequestMapping("/lastPage")
    public String lastPage(SearchForm searchForm, Model model){
        //���݃y�[�W�����ŏI�y�[�W�ɐݒ肷��
        searchForm.setCurrentPageNum(demoService.getAllPageNum(searchForm));
        return movePageInList(model, searchForm);
    }

    /**
     * �X�V�������s����ʂɑJ�ڂ���
     * @param id �X�V�Ώۂ�ID
     * @param model Model�I�u�W�F�N�g
     * @return ���́E�X�V��ʂւ̃p�X
     */
    @RequestMapping("/update")
    public String update(@RequestParam("id") String id, Model model){
        //�X�V�Ώۂ̃��[�U�[�f�[�^���擾
        DemoForm demoForm = demoService.findById(id);
        //���[�U�[�f�[�^���X�V
        model.addAttribute("demoForm", demoForm);
        return "input";
    }

    /**
     * �폜�m�F��ʂɑJ�ڂ���
     * @param id �X�V�Ώۂ�ID
     * @param model Model�I�u�W�F�N�g
     * @return �폜�m�F��ʂւ̃p�X
     */
    @RequestMapping("/delete_confirm")
    public String delete_confirm(@RequestParam("id") String id, Model model){
        //�폜�Ώۂ̃��[�U�[�f�[�^���擾
        DemoForm demoForm = demoService.findById(id);
        //���[�U�[�f�[�^���X�V
        model.addAttribute("demoForm", demoForm);
        return "confirm_delete";
    }

    /**
     * �폜�������s��
     * @param demoForm �ǉ��E�X�V�pForm�I�u�W�F�N�g
     * @param searchForm �����pForm�I�u�W�F�N�g
     * @param model Model�I�u�W�F�N�g
     * @return �ꗗ��ʂ̕\������
     */
    @RequestMapping(value = "/delete", params = "next")
    public String delete(DemoForm demoForm, SearchForm searchForm, Model model){
        //�w�肵�����[�U�[�f�[�^���폜
        demoService.deleteById(demoForm.getId());
        //�ꗗ��ʂɖ߂�A1�y�[�W�ڂ̃��X�g��\������
        searchForm.setCurrentPageNum(1);
        return movePageInList(model, searchForm);
    }

    /**
     * �폜�m�F��ʂ���ꗗ��ʂɖ߂�
     * @param model Model�I�u�W�F�N�g
     * @param searchForm �����pForm�I�u�W�F�N�g
     * @return �ꗗ���
     */
    @RequestMapping(value = "/delete", params = "back")
    public String confirmDeleteBack(Model model, SearchForm searchForm){
        //�ꗗ��ʂɖ߂�
        return movePageInList(model, searchForm);
    }

    /**
     * �ǉ��������s����ʂɑJ�ڂ���
     * @param model Model�I�u�W�F�N�g
     * @return ���́E�X�V��ʂւ̃p�X
     */
    @RequestMapping(value = "/add", params = "next")
    public String add(Model model){
        model.addAttribute("demoForm", new DemoForm());
        return "input";
    }

    /**
     * �ǉ��������s����ʂ��猟����ʂɖ߂�
     * @return ������ʂւ̃p�X
     */
    @RequestMapping(value = "/add", params = "back")
    public String addBack(){
        return "search";
    }

    /**
     * �G���[�`�F�b�N���s���A�G���[��������Ίm�F��ʂɑJ�ڂ��A
     * �G���[������Γ��͉�ʂ̂܂܂Ƃ���
     * @param demoForm �ǉ��E�X�V�pForm�I�u�W�F�N�g
     * @param result �o�C���h����
     * @return �m�F��ʂ܂��͓��͉�ʂւ̃p�X
     */
    @RequestMapping(value = "/confirm", params = "next")
    public String confirm(@Valid DemoForm demoForm, BindingResult result){
        //���N�����̓��t�`�F�b�N�������s���A��ʑJ�ڂ���
        return demoService.checkForm(demoForm, result);
    }

    /**
     * �ꗗ��ʂɖ߂�
     * @param model Model�I�u�W�F�N�g
     * @param searchForm �����pForm�I�u�W�F�N�g
     * @return �ꗗ��ʂ̕\������
     */
    @RequestMapping(value = "/confirm", params = "back")
    public String confirmBack(Model model, SearchForm searchForm){
        //�ꗗ��ʂɖ߂�
        return movePageInList(model, searchForm);
    }

    /**
     * ������ʂɑJ�ڂ���
     * @param demoForm �ǉ��E�X�V�pForm�I�u�W�F�N�g
     * @param sessionStatus �Z�b�V�����X�e�[�^�X
     * @return �������
     */
    @RequestMapping(value = "/send", params = "next")
    public String send(DemoForm demoForm, SessionStatus sessionStatus){
        //���[�U�[�f�[�^������΍X�V���A������΍폜
        demoService.createOrUpdate(demoForm);
        //�Z�b�V�����I�u�W�F�N�g��j��
        sessionStatus.setComplete();
        return "complete";
    }

    /**
     * ���͉�ʂɖ߂�
     * @return ���͉��
     */
    @RequestMapping(value = "/send", params = "back")
    public String sendBack(){
        return "input";
    }

    /**
     * �ꗗ��ʂɖ߂�A�w�肵�����݃y�[�W�̃��X�g��\������
     * @param model Model�I�u�W�F�N�g
     * @param searchForm �����pForm�I�u�W�F�N�g
     * @return �ꗗ��ʂ̕\������
     */
    private String movePageInList(Model model, SearchForm searchForm){
        //���݃y�[�W��, ���y�[�W����ݒ�
        model.addAttribute("currentPageNum", searchForm.getCurrentPageNum());
        model.addAttribute("allPageNum", demoService.getAllPageNum(searchForm));
        //�y�[�W���O�p�I�u�W�F�N�g�𐶐����A���݃y�[�W�̃��[�U�[�f�[�^���X�g���擾
        Pageable pageable = demoService.getPageable(searchForm.getCurrentPageNum());
        List<DemoForm> demoFormList = demoService.demoFormList(searchForm, pageable);
        //���[�U�[�f�[�^���X�g���X�V
        model.addAttribute("demoFormList", demoFormList);
        return "list";
    }
}